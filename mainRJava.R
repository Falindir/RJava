
foo = function(mat) {
    if(is.matrix(mat)){
      mat <- mat+10
      return (mat)
    } else {
        return (matrix(data=NA, nrow = 1, ncol=1))
    }
}

bar = function() {

    print("Hello from R with 'print'")

    cat("Test with 'cat'")

}

stp = function() {
  print("before")
  stop()
  print("after")
}
