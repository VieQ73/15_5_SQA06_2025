var Shop = {
		
		chon_san_pham_dua_vao_gio_hang: function(productId, soluong) {
			var data = {};
			data["productId"] = productId;
			data["soluong"] = soluong;
			
			$.ajax({
				url: "/chon-san-pham-dua-vao-gio-hang",
				type: "post",
				contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
				data: JSON.stringify(data), // object json -> string json
				
				dataType: "json", // dữ liệu từ web-service trả về là json.
				success: function(jsonResult) { // được gọi khi web-service trả về dữ liệu.
					if(jsonResult.status == 200) {
						//alert(jsonResult.data);
						
						$("#so_luong_sp").html(jsonResult.data);
						alert('Thêm vào giỏ hàng thành công');
						
					} else {
						alert('loi');
					}
				},
				error: function (jqXhr, textStatus, errorMessage) { // error callback 
			        
			    }
			});
		},
		/*Xóa sản phẩm trong giỏ hàng*/
		xoa_sp_trong_gio_hang: function(productId) {
			var data = {};
			data["productId"] = productId;
			
			
			$.ajax({
				url: "/xoa-sp-gio-hang",
				type: "post",
				contentType: "application/json", 
				data: JSON.stringify(data), 
				
				dataType: "json", 
				success: function(jsonResult) { 
					if(jsonResult.status == 200) {
						
						$('#sp'+productId).remove();
						$("#tongtienT").html(jsonResult.data +'đ');
				
					} else {
						alert('Lỗi');
					}
				},
				error: function (jqXhr, textStatus, errorMessage) { // error callback 
			        
			    }
			});
		},
		
		
		/**
		 * https://www.w3schools.com/js/js_json_syntax.asp
		 */
		saveContact: function() {
			var data = {};
			data["name"] = $("#name").val();
			data["email"] = $("#email").val();
			data["message"] = $("#message").val();
			
			$.ajax({
				url: "/save-contact-with-ajax",
				type: "post",
				contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
				data: JSON.stringify(data), // object json -> string json
				
				dataType: "json", // dữ liệu từ web-service trả về là json.
				success: function(jsonResult) { // được gọi khi web-service trả về dữ liệu.
					//alert(jsonResult.message);
					//$("#message").html(jsonResult.data);
					//$('#blogModal').modal('show');
					if(jsonResult.status == 200) {
						alert(jsonResult.data);
						$("#name").val("");
						$("#email").val("");
						$("#message").val("");
					} else {
						alert('loi');
					}
				},
				error: function (jqXhr, textStatus, errorMessage) { // error callback 
			        
			    }
			});
		},
		confirm_saleProduct: function(idP) {
			var data = {};
			data["id"] = idP;
			$.ajax({
				url: "/confirm_saleProduct",
				type: "post",
				contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
				data: JSON.stringify(data), // object json -> string json
				
				dataType: "json", // dữ liệu từ web-service trả về là json.
				success: function(jsonResult) { // được gọi khi web-service trả về dữ liệu.
					if(jsonResult.status == 200) {
						location.reload();
					} else {
						alert('Xác nhận không thành công!');
					}
				},
				error: function (jqXhr, textStatus, errorMessage) { // error callback 
			        
			    }
			});
		},
		
}