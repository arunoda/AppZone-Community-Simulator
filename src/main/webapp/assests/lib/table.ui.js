function Table(div){
	this.isDraw=false;
	this.heading;
	this.setHeading=function(heading){
		this.heading=heading;
	}
	this.count=0;
	this.callbacks={};
	
	this.draw=function(){
		var html=
			"<table class='code51Table'>" +
			"	<tr class='tableHead'>";
			
		for(index in this.heading){
			var head=this.heading[index];
			html+="<td class='"+head.name+"'>"+head.title+"</td>";
		}
		
		html+="	</tr>" +
			"</table>";
		$(div).html(html);
		this.reloadCSS();
	}
	
	this.prependRow = function(row) {
		var html = 
		"<tr id='"+this.count +"'>" +
		"	<td class='id'>"+this.count+"</td>";
		
		for(headIndex in this.heading){
			var head=this.heading[headIndex];
			html+="<td class='"+head.name+"'>"+row[headIndex]+"</td>";
		}
		
		html += "</tr>"
		
		$(div + " .code51Table .tableHead").after(html);
	}
	
	this.loadRows=function(rows){
		var table=$(div+" .code51Table");
		var html="";
			
		for(rowIndex in rows){
			this.count++;
			html+="<tr id='"+this.count +"'>"+
			"	<td class='id'>"+this.count+"</td>";
			
			var row=rows[rowIndex];
			
			for(headIndex in this.heading){
				var head=this.heading[headIndex];
				html+="<td class='"+head.name+"'>"+row[headIndex]+"</td>";
			}
			
			html+="</tr>";
		}
		
		
		table.append(html);
		this.reloadCSS();
		this.registerEvent();
	}
	
	this.deleteAll=function(){
		var head=$(div+' .code51Table .tableHead');
		$(div+' .code51Table tr').detach();
		$(div+' .code51Table').append(head);
		this.count=0;
	};
	
	this.deleteRows=function(rows){
		for(index in rows){
			$(div+' .code51Table #'+rows[index]).detach();
		}
		this.count-=rows.length;
	}
	
	this.getRow=function(index){
		var resp={};
		var data=$(div+' .code51Table #'+index+' td').get();
		for(index in data){
			col=$(data[index]).attr('class');
			val=$(data[index]).text();
			resp[col]=val;
		}
		
		return resp;
	}
	
	this.onRowClick=function(callBack){
		this.callbacks.rowClick=callBack;		
	};
	
	this.registerEvent=function(){
		
		var callbacks = this.callbacks;
		//send the callback with the items in the row cliked
		var modifiedCallback = function () {
			if(!this.id) return;
			var data=$(div + " .code51Table #" + this.id +  " td").get();
			var resp = {}
			for(index in data) {
				col=$(data[index]).attr('class');	
				val=$(data[index]).text();
				resp[col]=val;	
			}
			
			if(callbacks['rowClick']) {
				callbacks.rowClick(resp)
			}
		}
		
		$(div+" .code51Table tr").unbind('click');
		$(div+" .code51Table tr").click(modifiedCallback);
	}
	
	this.reloadCSS=function(){
		$(div+" .code51Table tr").
			css("cursor","pointer");
		;
		$(div+" .code51Table .tableHead").
			css("cursor","default");
		
		for(index in this.heading){
			var head=this.heading[index];
			$(div+" .code51Table tr ."+head.name).css('width',head.width);
		}
	}
	
}
