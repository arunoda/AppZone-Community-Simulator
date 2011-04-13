/*
	Copyright 2010 Arunoda Susiripala

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

$(document).ready(function() {
	$('#contentBar').tabs();
	$('button').button();
	$('#phoneList').tabs();
	displaySimulatorInfo();
	enableToolTipSupport();
});

function displaySimulatorInfo() {
	var url = location.href
	if (url.substr(url.length - 1, url.length) != "/")
		url += "/"
			url += "simulator"
				$('#heading #simulator-info input').attr('value', url);
}

function enableToolTipSupport() {
	$('#btn-config').tooltip().dynamic();
	$('#contentBar a').tooltip();
	$('#heading #simulator-info').tooltip().dynamic();
	$('input[title]').tooltip().dynamic();

}

/*******************************************************************************
 * LOG VIEW
 ******************************************************************************/
var maxLogDate = 0
//the maximum date a log has
var table = new Table("#logs #serverLog");

$(document).ready(function() {

	createLogTable();
	getServerLog();
});

function getServerLog() {

	$.get('service?service=mtlog&since=' + maxLogDate, function(resp) {
		var response = null;
		eval('response=' + resp);

		for (index in response) {
			var message = response[index]
			                       var time = parseFloat(message.receivedDate)
			                       var date = new Date(time)

			// adding items to the table
			var item = [ date.format("dd-mm-yyyy @ HH:MM:ss"),
			             escapeHTML(message.message), message.addresses.join(",") ]

			             table.prependRow(item)

			             // coz now logs are comming as decending order
			             if (time > maxLogDate)
			            	 maxLogDate = time
		}
	});

	setTimeout('getServerLog();', 1000)
}

function createLogTable() {

	table.setHeading( [ {
		name : "date",
		title : "Received Date",
		width : 170
	}, {
		name : "message",
		title : "Message",
		width : 400
	}, {
		name : "address",
		title : "Address",
		width : 300
	}, ]);
	table.draw();
}

/*******************************************************************************
 * Phones
 ******************************************************************************/
var totalPhones = 0;
var inboxes = {}
var maxSmsLogDates = []

                      $(document).ready(function() {
                    	  $('#phones #newPhone').click(function() {
                    		  var phoneNo = $('#phones #phoneNo').attr('value');
                    		  // validation
                    		  if (phoneNo.trim() == "") {
                    			  alert("Please enter the Phone Number");
                    			  $('#phones #phoneNo').focus();
                    			  return;
                    		  }

                    		  // notify server about the creation of the phone
                    		  $.get('service?service=phone&number=' + phoneNo, function(resp) {
                    			  var response = {}
                    			  eval("response = " + resp);
                    			  new Phone(response.md5PhoneNo, phoneNo);
                    			  $('#phones #phoneNo').attr('value', '');
                    			  totalPhones++;
                    		  });
                    	  });

                      });

function Phone(md5PhoneNo, phoneNo) {

	// Phone UI Creation
	$phoneDiv = '<div id="' + md5PhoneNo + '" class="phone">'
	+ $('#samplePhone').html() + '</div>';
	$('#phoneList').append($phoneDiv);
	var index = $('#phoneList').tabs('add', "#" + md5PhoneNo, phoneNo);
	$('#phoneList').tabs('select', totalPhones);
	$('#' + md5PhoneNo + ' .message').focus();
	// display md5Name Info
	$('#' + md5PhoneNo + ' .md5Name').text(
			"Phone No Internally Represent as: " + md5PhoneNo);
	$('#' + md5PhoneNo + ' .md5Name').tooltip().dynamic();

	// create the inbox
	var inbox = new Table('#' + md5PhoneNo + ' .inbox');
	inbox.setHeading( [ {
		name : "date",
		title : "Received Date",
		width : 170
	}, {
		name : "message",
		title : "Message",
		width : 400
	} ]);
	inbox.draw();
	inboxes[md5PhoneNo] = inbox
	maxSmsLogDates[md5PhoneNo] = 0

	// load the sms's to the inbox
	getSMSLog(md5PhoneNo);

	// Send MO Functionality
	$('#' + md5PhoneNo + ' .send')
	.click(
			function() {

				var callback = function(resp) {
					var response = {}
					eval("response = " + resp)
					if (response['error']) {
						alert(response.error
								+ "\nPlease Configure the Simulator for Receiver URL")
					} else {
						alert("Messege Sent!");
					}

					$('#' + md5PhoneNo + ' .message').attr('value', '')
					$('#' + md5PhoneNo + ' .message').focus()
				}

				var message = $('#' + md5PhoneNo + ' .message').attr(
				'value')
				
				$.get('service?service=sendmo&address=' + md5PhoneNo
						+ '&message=' + escape(message), callback);
			});

}

function getSMSLog(md5PhoneNo) {

	var callback = function(resp) {

		var response;
		eval("response = " + resp)
		for (index in response) {
			var sms = response[index]
			                   var time = parseFloat(sms.receivedDate)
			                   var date = new Date(time)
			// adding item to the table
			var item = [ date.format("dd-mm-yyyy @ HH:MM"),
			             escapeHTML(sms.message) ];
			inboxes[md5PhoneNo].prependRow(item)

			if (time > maxSmsLogDates[md5PhoneNo])
				maxSmsLogDates[md5PhoneNo] = time
		}

		setTimeout('getSMSLog("' + md5PhoneNo + '")', 1000);
	}

	$.get('service?service=sms&address=' + md5PhoneNo + "&since="
			+ maxSmsLogDates[md5PhoneNo], callback);
}

/*******************************************************************************
 * SESSION
 ******************************************************************************/

$(document).ready(function() {

	// load default values to the Configuration Box
	$.get('service?service=app&infoRequest=true', function(resp) {
		var response = {}
		eval("response = " + resp)

		$('#createSession #appName').attr('value', response.username)
		$('#createSession #password').attr('value', response.password);
		$('#createSession #reciever').attr('value', response.url);
	})

	$('#btn-config').click(function() {
		$("#createSession").dialog( {
			width : 450,
			height : 200,
			modal : true
		});

		// use 2 times inorder get focus on the appId field with the
		// tooltip
		$('#createSession #appName').focus();
		$('#createSession #appName').focus();

	});

	$('#createSessionBtn').click(
			function() {
				$appName = $('#createSession #appName').attr('value');
				$password = $('#createSession #password').attr('value');
				$reciever = $('#createSession #reciever').attr('value');

				$.get('service?service=app&action=create&username='
						+ $appName + '&password=' + $password + '&url='
						+ $reciever, function($rest) {
							alert("Simulator Configured!");
						});

				$("#createSession").dialog('close');
			});
});

/*******************************************************************************
 * Utility Functions
 ******************************************************************************/

function escapeHTML(str) {
	var div = document.createElement('div');
	var text = document.createTextNode(str);
	div.appendChild(text);
	return div.innerHTML;
};
