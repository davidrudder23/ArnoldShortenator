$(document).ready(function(){
    $('.typeahead').keyup(function() {
        getMatches($(this).val())
    })
});

function getMatches(slug) {
    if (slug.length < 1)
        return;

    $("#add-button").html("Add");

    $.ajax({
        url:"/api/search/"+encodeURIComponent(slug),
        success: function(data) {
            console.log(data);
            var results = "";
            var foundExactMatch = false;
            $.each(data, function(index, value) {
                console.log(value.fullSourceURL);
                console.log(value.destinationURL);
                if (value.slug == slug) {
                    $("#destination").val(value.destinationURL);
                    $("#add-button").html("Edit");
                }
                results += `
                        <div class="col-lg-6">
                                        <h4>`+value.slug+`&nbsp;<i class="bi bi-trash" onclick='deleteMapping("`+value.slug+`")'></i></h4>
                                        <p><a href="/`+value.slug+`">`+value.destinationURL+`</a></p>
                                    </div>
                                    `;
            });

            $("#typeahead-results").html(results);

        }
    })
}

