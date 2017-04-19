setClass(
  
  Class="VoxHeader",
  representation=representation(
    mincorner="Point3d",
    maxcorner="Point3d",
    split="Point3d",
    columnNames="vector",
    extra="character"
  )
)

setClass(
  
    Class="VoxelSpace",
    representation=representation(
      file="character",
      header="VoxHeader",
      voxels="list"
    )
)

setClass(
  
  Class="Point3d",
  representation=representation(
    x="numeric",
    y="numeric",
    z="numeric"
  )
)

loadVoxelSpace <- function(file){
  
  instance=new(Class=("VoxelSpace"))
  
  #lecture du header
  conn <- file(file,open="r")
  linn <-readLines(conn, n = 6)
  close(conn)
  
  minCorner = unlist(strsplit(linn[2], " ", fixed=F))
  instance@header@mincorner = new(Class=("Point3d"), x=as.numeric(minCorner[2]), y=as.numeric(minCorner[3]), z=as.numeric(minCorner[4]))
  
  maxCorner = unlist(strsplit(linn[3], " ", fixed=F))
  instance@header@maxcorner = new(Class=("Point3d"), x=as.numeric(maxCorner[2]), y=as.numeric(maxCorner[3]), z=as.numeric(maxCorner[4]))
  
  split = unlist(strsplit(linn[4], " ", fixed=F))
  instance@header@split = new(Class=("Point3d"), x=as.numeric(split[2]), y=as.numeric(split[3]), z=as.numeric(split[4]))
  
  instance@header@extra = linn[5]
  
  columnNames = unlist(strsplit(linn[6], " ", fixed=F))
  instance@header@columnNames = columnNames
  
  #lecture des voxels
  #instance@voxels= read.table(file, header=T,skip=5)
  instance@voxels = fread(file,header = T,skip=5)
  
  return (instance)
}

writeVoxelSpace <- function(voxelSpace, outputFile){
  
  conn <- file(outputFile, open="w")
  
  writeLines("VOXEL SPACE", conn)
  writeLines(paste("#min_corner:", voxelSpace@header@mincorner@x, voxelSpace@header@mincorner@y, voxelSpace@header@mincorner@z, sep = " "), conn)
  writeLines(paste("#max_corner:", voxelSpace@header@maxcorner@x, voxelSpace@header@maxcorner@y, voxelSpace@header@maxcorner@z, sep = " "), conn)
  writeLines(paste("#split:", voxelSpace@header@split@x, voxelSpace@header@split@y, voxelSpace@header@split@z, sep = " "), conn)
  writeLines(voxelSpace@header@extra, conn)
  
  close(conn)
  
  write.table(voxelSpace@voxels, outputFile, row.names=F, col.names=T, na="NaN",sep=" ", append=T, quote=F)
}
