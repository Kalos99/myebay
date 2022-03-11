<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	   
	   <title>Ricerca</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>Ricerca utenti</h5> 
				    </div>
				    <div class='card-body'>
		
							<form method="post" action="${pageContext.request.contextPath }/annuncio/ExecuteSearchAnnunciUtenteServlet" class="row g-3" >
							
								<div class="col-md-6">
									<label for="testo" class="form-label">Testo </label>
									<input type="text" class="form-control" name="testo" id="testo" placeholder="Inserire il testo"  >
								</div>
										
								<div class="col-md-6">
									<label for="prezzo" class="form-label">Prezzo </label>
									<input type="number" class="form-control" name="prezzo" id="prezzo" placeholder="Inserire il prezzo" >
								</div>
								
								<div class="col-md-6">
									<label for="dataPubblicazione" class="form-label">Data pubblicazione annuncio </label>
                        			<input class="form-control" id="dataPubblicazione" type="date" placeholder="dd/MM/yy"
                            			title="formato : gg/mm/aaaa"  name="dataPubblicazione"   >
								</div>
								
								<div class="row-md-6">
									<p>Stato:</p>
									<div class="form-check">
									  <input class="form-check-input" type="radio" name="stato" id="flexRadioDefault1" value="true" checked>
									  <label class="form-check-label" for="flexRadioDefault1">
									    aperto
									  </label>
									</div>
									
									<div class="form-check">
									<input class="form-check-input" type="radio" name="stato" id="flexRadioDefault2" value="false">
									  <label class="form-check-label" for="flexRadioDefault2">
									    chiuso
									  </label>
									</div>
								</div>
									
								<div class="row-md-6">	
								<p>Categoria:</p>	
									<c:forEach items="${mappaCategorieConSelezionati_attr}" var="categoriaEntry">
										<div class="form-check">
											  <input class="form-check-input" name="categorie" type="checkbox" value="${categoriaEntry.key.id}" id="categorie-${categoriaEntry.key.id}" ${categoriaEntry.value?'checked':'' }>
											  <label class="form-check-label" for="categorie-${categoriaEntry.key.id}" >
											  ${categoriaEntry.key.codice}
											  </label>
										</div>
									</c:forEach>
								</div>
								
							<div class="col-12">
								<input type="hidden" name="idUtente" value="${userInfo.id}">
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
								<a class="btn btn-outline-primary ml-2" href="PrepareInsertAnnuncioServlet">Add New</a>
								<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
							</div>
		
						</form>
  
				    
				    
					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>		
					  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>