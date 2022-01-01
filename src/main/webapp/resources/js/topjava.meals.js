const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: mealAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                            return date.toString().replace("T", '   ');
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                // debugger;
                if (data.excess) {
                    $(row).css( 'color', 'red' );
                } else {
                    $(row).css( 'color', 'green' );
                }
            }
        })
    );
});

$(function(){
    let startDate = $('#startDate');
    let endDate = $('#endDate');

   startDate.datetimepicker({
        format:'Y/m/d',
        formatDate:'Y/m/d',
        onShow:function( ct ){
            this.setOptions({
                maxDate : endDate.val() ? endDate.val() : false
            })
        },
        timepicker:false
    });

    endDate.datetimepicker({
        format:'Y/m/d',
        formatDate:'Y/m/d',
        onShow:function( ct ){
            this.setOptions({
                minDate : startDate.val() ? startDate.val() : false
            })
        },
        timepicker:false
    });
});