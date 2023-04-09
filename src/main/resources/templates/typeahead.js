$(document).ready(function(){
    $('.typeahead').keyup(function() {
        getMatches($(this).val())
    })
});

function getMatches(slug) {
    if (slug.length < 3)
        return;
            $("#add").hide();

    $.ajax({
        url:"/api/search/"+slug,
        success: function(data) {
            console.log(data);
            var results = "";
            var foundExactMatch = false;
            $.each(data, function(index, value) {
                console.log(value.fullSourceURL);
                console.log(value.destinationURL);
                if (value.slug == slug) {
                    foundExactMatch = true;
                }
                results += `
                        <div class="col-lg-6">
                                        <h4><a href="/`+value.slug+`">`+value.slug+`</a></h4><i class="bi bi-trash" onclick='deleteMapping("`+value.slug+`")'></i>
                                        <p>`+value.destinationURL+`</p>
                                    </div>
                                    `;
            });

            $("#typeahead-results").html(results);

            if (!foundExactMatch) {
                $("#add").show();
            }
        }
    })
}

