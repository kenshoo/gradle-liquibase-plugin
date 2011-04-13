<%= methodDesc%>
<%if(!paramLists.flatten().isEmpty()) {%>Can be invoked by providing following options:
<%paramLists.findAll{!it.isEmpty()}.each{ list ->
   def params = list.inject('') { r,p -> r << "-P${p.name}=[${p.type.javaClass.name} value] " }
   println "${name} ${params}"
   }%>
<%}%>
