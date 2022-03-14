<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	   
	   <title>Inserisci nuovo annuncio</title>
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
							        <h5>Inserisci il tuo nuovo annuncio</h5> 
							    </div>
							    <div class='card-body'>
					
										<form method="post" action="${pageContext.request.contextPath }/annuncio/ExecuteInsertAnnunciServlet" class="row g-3" >
										
											<div class="col-md-6">
												<label for="testo" class="form-label">Testo </label>
												<input type="text" class="form-control" name="testo" id="testo" placeholder="Inserire il testo"  >
											</div>
										
											<div class="col-md-6">
												<label for="prezzo" class="form-label">Prezzo </label>
												<input type="number" class="form-control" name="prezzo" id="prezzo" placeholder="Inserire il prezzo" >
											</div>
											
											<c:forEach items="${mappaCategorieConSelezionati_attr}" var="categoriaEntry">
													<div class="form-check">
														  <input class="form-check-input" name="categorie" type="checkbox" value="${categoriaEntry.key.id}" id="ruoli-${categoriaEntry.key.id}" ${categoriaEntry.value?'checked':'' }>
														  <label class="form-check-label" for="categorie-${categoriaEntry.key.id}" >
														    ${categoriaEntry.key.codice}
														  </label>
													</div>
											  	</c:forEach>
											
										<div class="col-6">
											<input type="hidden" name="idUtente" value="${userInfo.id}">
											<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Inserisci</button>
											<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
											<a href="${ pageContext.request.contextPath }/annuncio/ExecuteListAnnunciUtenteServlet?idUtente=${userInfo.id}" class='btn btn-outline-secondary' style='width:80px'>
									            <i class='fa fa-chevron-left'></i> Back
									        </a>
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