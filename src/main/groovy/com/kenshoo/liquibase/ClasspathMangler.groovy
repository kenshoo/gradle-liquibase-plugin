class ClasspathMangler {
  def readResourceText(resource) {
  	this.class.classLoader.getResourceAsStream(resource).text
  }  
}
