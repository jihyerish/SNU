$(document).on("pageshow", "#readyPage", function(){
    $("#ready").off("tap").on("tap", function(){
        MILE.send("ready", "Client ready");
    });
});
$(document).on("pageshow", "#background", function(){
    console.log('chage background');
    // MILE.send("playerInfo", "Request basic information");
    $("#otherPlayersList").off("tap").on('tap', function(){
        var tagName = event.target.tagName;
        var id = event.target.id;
        if(tagName ==="P"){
            var content = document.getElementById(id).innerHTML;
            MILE.send("otherPlayerInfo", content);
        }
    });
    $("#mountedCardsList").off("tap").on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            var content = $(event.target).attr('src');
            $.mobile.changePage('#mountedCardInfo', {data: {'imgsrc': content}});
        }
    });
    $("#inHandCardsList").off("tap").on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            var content = $(event.target).attr('src');
            MILE.send("inHandCardInfo", content);
        }
    });
});
$(document).on("pageshow", "#playerInfo", function(){
    $("#othersMountedCardsList").off("tap").on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            var content = $(event.target).attr('src');
            $.mobile.changePage('#mountedCardInfo', {data: {'imgsrc': content}});
        }
    });
    $('#back').off("tap").on('tap', function(){
        //$.mobile.changePage('#background');
        MILE.send("playerInfo", "Request basic information");
    });
});
$(document).on("pageshow", "#mountedCardInfo", function(){
    var content = $(this).data('imgsrc'); /* img src */
    var image = "<img src=\"" + content + "\">";
    $('#mountedCardImage').empty();
    $('#mountedCardImage').append(image);
    $("#help").off("tap").on("tap", function(){
        //var content = $(this).siblings("img").attr('src');
        alert("To check tags: " + content);
        MILE.send("help", content);
    });
});
$(document).on("pageshow", "#inHandCardInfo", function(){
    $("#help").off("tap").on("tap", function(){
        //var content = $(this).siblings("img").attr('src');
        alert("To check tags: " + content);
        MILE.send("help", content);
    });
    $("#select").off("tap").on("tap", function(){
        //var content = $(this).siblings("img").attr('src');
        alert("To check tags: " + content);
        MILE.send("select", content);
    });
});
$(document).on("pageshow", "#cardToSelect", function(){
    $("#selectCardsList").off("tap").on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            var content = $(event.target).attr('src');
            MILE.send("selectPlayingCard", content);
        }
    });
});
$(document).on("pageshow", "#cardToDiscard", function(){
    $("#discardCardsList").off("tap").on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            var content = $(event.target).attr('src');
            MILE.send("discardPlayingCard", content);
        }
    });
});
$(document).on("pageshow", "#willUseBang", function(){
    $('#bangYes').off("tap").on('tap', function(){
        MILE.send("bangRespond", "Yes");
    });
    $('#bangNo').off("tap").on('tap', function(){
        MILE.send("bangRespond", "Yes");
    });
});
$(document).on("pageshow", "#willUseMiss", function(){
    $('#missYes').off("tap").on('tap', function(){
        MILE.send("missRespond", "Yes");
    });
    $('#missNo').off("tap").on('tap', function(){
        MILE.send("missRespond", "Yes");
    });
});
$(document).on("pageshow", "#willUseBeer", function(){
    $('#beerYes').off("tap").on('tap', function(){
        MILE.send("beerRespond", "Yes");
    });
    $('#beerNo').off("tap").on('tap', function(){
        MILE.send("beerRespond", "Yes");
    });
});
$(document).on("pageshow", "#selectTarget", function(){
    $('#selectTargetList').off('tap').on('tap', function(){
        var tagName = event.target.tagName;
        var id = event.target.id;
        if(tagName ==="P"){
            var content = document.getElementById(id).innerHTML;
            MILE.send("selectTargetRespond", content);
        }
    });
});
$(document).on("pageshow", "#selectTargetCard", function(){
        $('#selectTargetCardList').off('tap').on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            var content = $(event.target).attr('src');
            MILE.send("selectTargetCardRespond", content);
        }
        });
});
$(document).on("pageshow", "#willUseBang", function(){
});
/* TODO: respond, select target/targetcard */
