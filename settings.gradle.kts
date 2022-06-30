if (File("../kotest").exists() && System.getenv("NO_INCLUDE") == null) {
   println("w: Including Kotest build, will substitute all Kotest stuff for currently checked out implementations")
   includeBuild("../kotest")
}
