 $(function (){
              $('#table-javascript').bootstrapTable({
                method: 'get',
                url: 'data2.json',
                cache: false,
                height: 500,
                striped: true,
                pagination: true,
                pageSize: 5,
                pageList: [5,10, 25, 50, 100, 200],
                search: true,
                showColumns: true,
                showRefresh: true,
                showToggle:true,
                minimumCountColumns: 2,
                clickToSelect: true,
                columns: [{
                    field: 'state',
                    checkbox: true
                }, {
                    field: 'id',
                    title: 'Item ID',
                    align: 'right',
                    valign: 'bottom',
                    sortable: true
                }, {
                    field: 'name',
                    title: 'Item Name',
                    align: 'center',
                    valign: 'middle',
                    sortable: true
                }, {
                    field: 'price',
                    title: 'Item Price',
                    align: 'left',
                    valign: 'top',
                    sortable: true
                }, {
                    field: 'operate',
                    title: 'Item Operate',
                    align: 'center',
                    valign: 'middle',
                    clickToSelect: false
                }]
            
            });
              
              $(window).resize(function () {
                  $('#table-javascript').bootstrapTable('resetView');
              });
              
    });