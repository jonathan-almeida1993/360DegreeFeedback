
 $(document).ready(function() {
	 	 
		$("#fileUploaderDetails").uploadFile({
      	  url : "FileUploadServlet",
          allowedTypes : "xls,xlsx",
          fileName : "myfile",
          dynamicFormData: function()
          {
        	  var companyName = $('#comAdminSelect :selected').text();
        		 companyName = companyName.substring(companyName .indexOf('-'),companyName .length).slice(2).trim();
        		 console.log(companyName);

          	var data ={ companyName:companyName};
          	return data;
          },
          onSubmit : function(files) {
        	  $("#eventsmessage").html($("#eventsmessage").html() + "<br/>Submitting:" + JSON.stringify(files));
              //$('.loader').show();
          },
          onSuccess: function (files,data,xhr) {
        	alert("File Upload Completed Succesfully");
        	$("#eventsmessage").html($("#eventsmessage").html() + "<br/>File Upload Completed Succesfully...");
         	alert(" RECIEVED DATA: "+(data));
            returnData = jQuery.parseJSON(data);
            $('#excelUploadReportTable tbody').empty();
            $.each(returnData.InvalidEntries,function(idx,obj){
            	var tr = $('<tr/>');
        		tr.append('<td>'+obj.FirstName+'</td>');
        		tr.append('<td>'+obj.LastName+'</td>');
        		tr.append('<td>'+obj.EmailId+'</td>');
        		$('#excelUploadReportTable tbody').append(tr);
            });
            $('.excelUploadModal').modal('show');
            
          },
          onError : function(files, status, errMsg) {
            $("#eventsmessage").html($("#eventsmessage").html() + "<br/>Error for: " + JSON.stringify(files));
          }
        });

 }); // document ready ends
 