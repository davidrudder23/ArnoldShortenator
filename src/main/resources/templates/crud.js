$(document).ready(function(){
    $("#add-button").click(function() {
        var slug = $("#slug").val();

        if (!(/^[a-zA-Z0-9\_\-]+$/.test(slug))) {
            error ("The slug may only be letters, numbers, - or _");
            $("#slug-validation-warning").fadeIn();
            return;
        }

        if (slug.length < 3) {
            $("#slug-validation-warning").fadeIn();
            return;
        }

        var destination = $("#destination").val();

        if (!isValidUrl(destination)) {
            $("#destination-validation-warning").fadeIn();
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
            url: "/api/"+encodeURIComponent(slug),
            success: function() {
                getMatches($("#slug").val());
            }
        });
}

function isValidUrl(string) {
  try {
    new URL(string);
    return true;
  } catch (err) {
    return false;
  }
}


function edit(slug) {
}