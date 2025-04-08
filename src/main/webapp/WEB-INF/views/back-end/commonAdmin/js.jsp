<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%>

<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/modernizr.custom.js"></script>

	<!-- Metis Menu -->
	<script src="${pageContext.request.contextPath}/js/metisMenu.min.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/SidebarNav.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/custom.js"></script>
	<script src="${pageContext.request.contextPath}/js/product.js"></script>
    <!-- Classie --><!-- for toggle left push menu script -->
    <script src="${pageContext.request.contextPath}/js/classie.js"></script>
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    
    <!-- //Classie --><!-- //for toggle left push menu script -->
   
    <!--scrolling js-->
    
    
    <!--//scrolling js-->
    
    <!-- Bootstrap Core JavaScript -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"> </script>
    <!-- //Bootstrap Core JavaScript -->
    <script>
      $('.sidebar-menu').SidebarNav()
    </script>
     <script>
    	var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),
    	showLeftPush = document.getElementById( 'showLeftPush' ),
    	body = document.body;
    	
    	showLeftPush.onclick = function() {
    		classie.toggle( this, 'active' );
    		classie.toggle( body, 'cbp-spmenu-push-toright' );
    		classie.toggle( menuLeft, 'cbp-spmenu-open' );
    		disableOther( 'showLeftPush' );
    		
    	};
    	

    	function disableOther( button ) {
    		if( button !== 'showLeftPush' ) {
    			classie.toggle( showLeftPush, 'disabled' );
    		}
    	}
    </script>
    <script>
    	function showHide(){
    		$(".main-content").css("margin-left","0rem;");
    		console.log("hihi");
    	}
    </script>