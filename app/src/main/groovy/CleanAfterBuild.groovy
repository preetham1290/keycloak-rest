def cleanFiles(src,distPath){
	new File(src).eachFile { f ->  
    if(f.isFile()){
    	f.delete()
    }
}
	new File("${src}/assets").deleteDir()
	new File(distPath).deleteDir()
}

cleanFiles("src/main/webapp","src/main/webapp/key-clock-client/dist")