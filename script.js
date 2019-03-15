var r = document.getElementById('result');
function startConverting(){
if ( 'webkitSpeechRecognition' in window){

	var speechRecogniser = new webkitSpeechRecognition();
	speechRecogniser.continuous = true;
	speechRecogniser.interimResults = true;
	speechRecogniser.lang = 'en-IN';
	speechRecogniser.start();

	var finalTrans = '';
	speechRecogniser.onresult = function(event){
		var interimTrans = '';
		for(var i=event.resultIndex ; i<event.results.length;i++){
			var trans = event.results[i][0].transcript;
			trans.replace("\n","<br>")
			if(event.results[i].isFinal){
				finalTrans += trans;
			}else{
				interimTrans += trans;
			}
		}
		r.innerHTML = finalTrans + '<span style = "color:gray">' + interimTrans + '</span>'
	};  
	speechRecogniser.onerror = function(event){

	};

}else{
	r.innerHTML = "Your browser not supported"
}
};