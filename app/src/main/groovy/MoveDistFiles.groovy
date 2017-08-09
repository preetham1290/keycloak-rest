import java.io.File

def updateBaseHref(indexPath) {
 File file = new File("${indexPath}/index.html");
 def fileText = file.text;
 fileText = fileText.replaceAll('<base href="/">', '<base href="./">');
 file.write(fileText);
}

def destFolder = "";

def moveFilesFromDistToWebapp(srcPath,destPath) {

	def write = {
	 data,
	 lenth ->
	 output.write(data, 0, lenth)
	}

	def fileCopyClosure = {
	 if (it.isFile() && it.canRead()) {
	  println "new file to be created ${destFolder}/${it.getName()}"
	  def desti = new File("${destFolder}/${it.getName()}")
	  output = desti.newOutputStream()
	  it.eachByte(1024, write)
	  output.close()
	 } else {
	  println "file cannot be read ${it.getName()}"
	 }
	}

	
	 new File(srcPath).eachDirRecurse() {
	  dir ->
	   String[] str = dir.getPath().split('dist')
	  println str[1]
	  destFolder = new File("${destPath}/${str[1]}")
	  if (!destFolder.exists()) {
	   println("Creating new destination directory  ${destFolder}")
	   destFolder.mkdir()
	  }
	  dir.eachFileMatch(~/.*.*/, fileCopyClosure)
	 }
	 
	 destFolder = destPath
	 
	 new File(srcPath).eachFileMatch(~/.*.*/, fileCopyClosure)

	 new File(srcPath).delete()
}

/**
 *	User application specific build configuration
 */

def src = "src/main/webapp/key-clock-client/dist"
def dest = "src/main/webapp"

updateBaseHref(src)
moveFilesFromDistToWebapp(src, dest)