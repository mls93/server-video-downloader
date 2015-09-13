$( document ).ready(function() {
    
$("#find_vid").change(function(){
	if ($(this).is(':checked'))
		$("#div_add_video").removeAttr('style')
	else
		$("#div_add_video").css('display','none')
	
})

$("#add_video").submit(function(event) {
	event.preventDefault();
	
	var $form = $( this ),
	url = $form.attr( 'action' );
	var artist_val = $form.find("input#vid_artist").val()
	var title_val = $form.find("input#vid_title").val()
	var extra_val = $form.find("input#vid_extra").val();
	var posting = $.post( url, {artist:artist_val, title: title_val, extra:extra_val} );
	posting.done(function( data ) {
		location.href='main'
	});
})

$(".li_video").each(function(i){
	$(this).click(function(event){		
		$(".video_td").html("")
		$("#video_td"+(i+1)).html("<video controls  name='media' style=' width:720px; height:480px'><source src='"+$("#descr"+(i+1)).html()+"'></source></video>")
	})
})
	
	
$(".wordretrieve").each(function(i){
	console.log($(this))
	$(this).submit(function(event) {
		  console.log('hallo2')
		  
		  /* stop form from submitting normally */
		  event.preventDefault();

		  /* get some values from elements on the page: */
		  var $form = $( this ),
			  url = $form.attr( 'action' );
		  var input = $form.find("input.videotitle")
		  
		  var count = i+1
		  /* Send the data using post */
		  var posting = $.post( url, { title: input.val()} );

		  /* Alerts the results */
		  posting.done(function( data ) {
			$("#videowords"+count).html(data)
		  });
	});
})
});
