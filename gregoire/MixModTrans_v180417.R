#mixed model estimation of local transmittance

rm(list=ls())
library(lme4)
library(data.table)
source("/home/jimmy/jimmy/test/rjava/gregoire/VoxelSpaceUtility.R")

#param?tres
minivox=5 #distance de voisinage (en cellules)
EP=0.01 # pas de discr?tisation des volumes ?l?mentaires
seuil_echantillonnage=10 #nbre minimum de cellules ?chantillonn?es pour appliquer le mod?le glmer
seuil_fusion=0 # nombre max de minivoxels -1 fusionn?s pour le calcul du voisinage

#lecture de l'espace voxel

file="/home/jimmy/jimmy/test/rjava/input/ALS_P15_1m.vox"
#file="/home/jimmy/jimmy/test/rjava/input/Paracou_tile40_2013.vox"
ini_vox=loadVoxelSpace(file)
vox=ini_vox@voxels

###################
time1 = Sys.time()
###################

vox$ijk=paste(vox$i,vox$j,vox$k,sep="_")

#first compute indices of neighbourhood; we consider neighbourhoods of minivox cells (subvoxels)
vox$I=floor(vox$i/minivox)
vox$J=floor(vox$j/minivox)
vox$K=floor(vox$k/minivox)

vox$trials=round(vox$bvEntering/EP)
vox$success=round(vox$bvEntering*vox$transmittance/EP)
vox$prop=vox$success/vox$trials

#drop only below ground voxels
vox=vox[which(round(vox$ground_distance)>0),]

###################
time1 = Sys.time()
###################

df = vox[,.(nbvox=.N
            , nbna = sum ( trials == 0))
         , c("I","J","K")
         ]

# setting the order to force merging to take place vertically rather than horizontally
df<-df[order(df$I,df$J,df$K),]

###################
time2 = Sys.time()
###################

res=c()
previous=c()
fus=0
# parcours des cubes de voisinage
# pas optimis? mais plus lisible que la version de fabian (qui est bcp plus rapide)



for (i in 1:dim(df)[1])
{
  #merge with previous unprocessed cube
  #test=vox[which(vox$I==df$I[i] & vox$J==df$J[i] & vox$K==df$K[i]),]
  I0=df$I[i]
  J0=df$J[i]
  K0=df$K[i]



  test=vox[I==I0 & J==J0 & K==K0,]

  test$pred=as.numeric(NA) #as.numeric required if not treated as boolean in data.table

  test=rbind(test, previous)

  test_nona=test[which(test$trials>0),]
  test_na=test[which(test$trials==0),]
  #if enough voxels documented in cube then update values of transmittance

  if (dim(test_nona)[1]>seuil_echantillonnage)
    {
    previous=c()
    #check that response is not constant if so use mean value and skip glmer
    if (sum(test_nona$prop==rep(1, dim(test_nona)[1]))==dim(test_nona)[1])
      {
      #all voxels are empty, => leave as they are
      test$pred=1
      }
    else
      {
      if (sum(test_nona$prop==rep(0, dim(test_nona)[1]))==dim(test_nona)[1])
         {
         # all voxels have zero transmittance (or NA)-> replace with NA
         #those voxels will be later treated using mean transmittance per vegetation layer
          test$pred=NA
         }
      else
        {
        mod1=glmer(prop~1|ijk, weights=trials,family=binomial(link="logit"), data=test_nona)
        test_nona$pred=predict(mod1,type="response")
        if(dim(test_na)[1]>0) #there are cases of unsampled voxels which values are set to local mean transmittance
          {
          test_na$pred=mean(test_nona$pred)

          test=rbind(test_nona,test_na)
          }
        else #no unssampled voxel
         {
          test=test_nona
          }
        }
      }
      if (is.null(res)) {res=test} else {res=rbind(res,test)}
      fus=0
    }
  else
    {
    if (fus < seuil_fusion)
      {
      previous<-test #merge with next neighborhood processed
      fus=fus+1
      }
    else
      {
      test$pred=NA #still not enough documented cells -> replace with NA's and move on
      previous=c()
      if (is.null(res)) {res=test} else {res=rbind(res,test)}
      fus=0
      }
    }
  if (i %% 100==0)
  {
   #gc()
   print(i)
  }
}




###################
time3 = Sys.time()
###################

# hist(res$pred, breaks=100)
# hist(res$transmittance, breaks=100)
# range(na.omit(res$pred))



ini=merge(ini_vox@voxels,res[,c("i","j","k","pred")], by=c("i","j","k"), all.x=T)

out_vox=ini_vox
out_vox@voxels=ini

#mean trans per height above ground layer
stop()
res$ground_distance=round(res$ground_distance)
mean_layer_trans=tapply(res$pred,res$ground_distance, mean, na.rm=T)
df_layer=data.frame(layer=as.numeric(names(mean_layer_trans)),mean_trans=mean_layer_trans)

#completerles donn?es manquantes de tansmittance dans la canop?e
out_vox_nona=out_vox@voxels[which((!is.na(out_vox@voxels$pred)) & (round(out_vox@voxels$ground_distance)>0)),]
out_vox_na=out_vox@voxels[which(is.na(out_vox@voxels$pred) & round(out_vox@voxels$ground_distance)>0),]

print(dim(df_layer)[1])
stop()
for (l in 1:dim(df_layer)[1])
{
  out_vox_na$pred[which(round(out_vox_na$ground_distance)==l)]<-df_layer$mean_trans[l]
}

temp=rbind(out_vox_nona,out_vox_na)
#nettoyer les bavures cad remplacer les transmittances > 0.99 par 1
temp$transmittance=temp$pred
hist(temp$transmittance)
temp$transmittance[which(temp$transmittance > 0.99)]=1
temp$PadBVTotal=-2*log(temp$pred)
temp=temp[,1:16]
out_vox_grd=out_vox@voxels[which(round(out_vox@voxels$ground_distance)<=0),]
out_vox@voxels=rbind(temp,out_vox_grd[,1:16])


###################
time4 = Sys.time()
###################


cat("numbering and qualifying minivoxels: ", difftime(time2,time1,units="secs"), " seconds\n")
cat("total time: ", difftime(time4,time1,units="mins"), "minutes\n")
cat("glmer estimation loop: ", difftime(time3,time2,units="mins"), "minutes\n")

#out_vox@voxels$transmittance[which(round(out_vox@voxels$ground_distance)<=0)]=0 #ground points, no transmittance
out_vox@voxels$PadBVTotal[which(round(out_vox@voxels$ground_distance)<=0)]=0 #ground points, no vegetation


writeVoxelSpace (out_vox,"d:/temp/ALS_P15/ALS_P15_1m_modR_fus0.vox")

