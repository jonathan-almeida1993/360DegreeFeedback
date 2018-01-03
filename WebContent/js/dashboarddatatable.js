$(document).ready(function() {
//	dashboard datatable
	$("#dashboardTable").dataTable({
      "aoColumnDefs": [{ "bSortable": false, "aTargets": [ 2 ] }]
    });

	var i =0;
	// Add Table Row
	$("#dashboardTable tbody").empty();
		$(".addDashModal .saveDashBtn").click(function(){
			alert($('#name').val()+'\n'+$('#position').val()); 
		 	if(i==0){
		 		$("#dashboardTable tbody").find("tr").remove();
		 		i++;
		 	}
		 	tr = $('<tr/>');
			tr.append("<td>" + $('#name').val() + "</td>");
			tr.append("<td>" + $('#position').val() + "</td>");
			tr.append("<td>" + "<button type='button' class='btn btn-danger editDashBtn'  data-toggle='tooltip' data-placement='bottom' title='Edit'><span class='glyphicon glyphicon-pencil'></span></button> <button type='button' class='btn btn-danger deleteDashBtn'  data-toggle='tooltip' data-placement='bottom' title='Delete'><span class='glyphicon glyphicon-trash'></span></button>" + "</td>");
			$(".addDashModal").modal('hide');
			$(".addDashModal form").trigger("reset");
			$('#dashboardTable tbody').append(tr);

//			dashboard datatable delete button
			$(".deleteDashBtn").click(function() {
				$(this).closest("tr").remove();	
			});
//			dashboard datatable edit button
			$(".editDashBtn").click(function() {
				var $tw = $(this).parents("tr");
				var name=$tw.find("td").eq(0).text();
				var position=$tw.find("td").eq(1).text();
				alert(name+" "+position);
				$(".editDashModal").modal('show');
				$("#editName").val(name);
				$("#editPosition").val(position);
			});
			
//			dashboard datatable edit button
			$(".updateDashBtn").click(function() {
				alert($('#editName').val()+'\n'+$('#editPosition').val());
				var editname = $("#editName").val();
				var editposition = $("#editPosition").val();
				$("#dashboardTable tbody td:eq(0)").html(editname);
				$("#dashboardTable tbody td:eq(1)").html(editposition);
				$(".editDashModal").modal('hide');
			});

		});
		

});