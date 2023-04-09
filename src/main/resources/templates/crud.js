$(document).ready(function(){
    $("#add-button").click(function() {
        var slug = $("#slug").val();
        var destination = $("#destination").val();

        if (slug.length < 3) {
            return;
        }

        if (destination.length < 3) {
            return;
        }

        $.ajax({
            type: "POST",
            url: "/api",
            contentType: 'application/json',
            data: JSON.stringify({
                "slug": $("#slug").val(),
                "destinationURL": $("#destination").val()
            }),
            success: function() {
            getMatches($("#slug").val())
            }
        });
    })
});

function deleteMapping(slug) {
console.log("deleting "+slug)
     $.ajax({
            type: "DELETE",
            url: "/api/"+slug,
            success: function() {
                getMatches($("#slug").val());
            }
        });
}

function edit(slug) {
}