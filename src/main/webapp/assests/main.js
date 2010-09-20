/*********************************
 * @author Arunoda Susiripala
 * @copyright Arunoda Susiripala
 * @licence http://www.gnu.org/licenses/gpl-3.0.txt
 */


$(document).ready(function(){
	$('#contentBar').tabs();
	$('button').button();
	$('#phoneList').tabs();
	displaySimulatorInfo()
});

function displaySimulatorInfo() {
	var url = location.href
	if(url.substr(url.length -1, url.length) != "/") url += "/"
	url += "simulator"
	$('#heading h2').html("runs on: " + url) ;
}	

/***************************************
 * LOG VIEW
 **************************************/
var maxLogDate = 0 //the maximum date a log has
var table = new Table("#logs #serverLog");

$(document).ready(function() {

	createLogTable();
	getServerLog();
});

function getServerLog() {
	
	$.get('service?service=mtlog&since=' + maxLogDate, function(resp){
		var response=null;
		eval('response=' + resp);
		
		for(index in response) {
			var message = response[index]
			var time = parseFloat(message.receivedDate)
			var date = new Date(time)
			var item = [date.format("dd-mm-yyyy @ HH:MM:ss"), message.message, message.addresses.join(",")]
						
			table.prependRow(item)
			
			//coz now logs are comming as decending order
			if(time > maxLogDate) maxLogDate = time 
		}	
	});

	setTimeout('getServerLog();',1000)
}

function createLogTable() {
	
	table.setHeading([
		{name:"date",title:"Received Date",width:170},
		{name:"message",title:"Message",width:400},
		{name:"address",title:"Address",width:300},
	]);
	table.draw();
}


/***************************************************
* Phones
***************************************************/
var totalPhones=0;
var inboxes = {}
var maxSmsLogDates = []

$(document).ready(function(){
	$('#phones #newPhone').click(function(){
		var phoneNo=$('#phones #phoneNo').attr('value');
		new Phone(phoneNo);
		$('#phones #phoneNo').attr('value','');
		totalPhones++;
	});
	
});

function Phone(phoneNo){
	
	//notify server about the creation of the phone
	$.get('service?service=phone&number='+phoneNo);
	
	//Phone UI Creation
	$phoneDiv=
		'<div id="'+phoneNo+'" class="phone">' + 
		$('#samplePhone').html() +
		'</div>';
	$('#phoneList').append($phoneDiv);
	var index=$('#phoneList').tabs('add',"#"+phoneNo,phoneNo);
	$('#phoneList').tabs('select',totalPhones);
	$('#'+phoneNo+' .message').focus();
	
	
	//create the inbox
	var inbox = new Table('#'+phoneNo+' .inbox');
	inbox.setHeading([
		{name:"date",title:"Received Date",width:170},
		{name:"message",title:"Message",width:400}
	]);
	inbox.draw();
	inboxes[phoneNo] = inbox
	maxSmsLogDates[phoneNo] = 0
	
	//load the sms's to the inbox
	getSMSLog(phoneNo);

	
	//Send MO Functionality
	$('#'+phoneNo+' .send').click(function(){
		
		var callback = function (resp) {
			
			alert("Messege Sent!");
			$('#' + phoneNo + ' .message').attr('value', '')
			$('#' + phoneNo + ' .message').focus()
		}
		
		var message = $('#' + phoneNo + ' .message').attr('value')

		$.get('service?service=sendmo&address=' + phoneNo + '&message=' + message, callback);
	});

}

function getSMSLog(phoneNo){
		
	var callback = function(resp) {
		
		var response;
		eval("response = " + resp)
		for(index in response) {
			var sms = response[index]
			var time = parseFloat(sms.receivedDate)
			var date = new Date(time)
			var item = [date.format("dd-mm-yyyy @ HH:MM"), sms.message];
			inboxes[phoneNo].prependRow(item) 
			
			if(time > maxSmsLogDates[phoneNo]) maxSmsLogDates[phoneNo] = time
		}	
		
		setTimeout('getSMSLog("' + phoneNo + '")', 1000);
	}
	
	$.get('service?service=sms&address=' + phoneNo + "&since=" + maxSmsLogDates[phoneNo],callback);
}

/****************************************************************
 *  SESSION 
 * *************************************************************/

$(document).ready(function(){
	
	$('#btn-config').click(function(){
		$("#createSession").dialog({
			width:450,
			height:200,
			modal: true
		});
		$('#createSession #appName').focus();
	});

	$('#createSessionBtn').click(function(){
		$appName=$('#createSession #appName').attr('value');
		$password=$('#createSession #password').attr('value');
		$reciever=$('#createSession #reciever').attr('value');

		$.get('service?service=app&action=create&username='+ $appName+
		'&password='+$password+'&url=' + $reciever,function($rest){
			alert("Simulator Configured!");
		});
		
		$("#createSession").dialog('close');
	});
});

