Vue.component('vue-calendar', {
    props: ['year', 'month'],
    template: '<div class="vue-calendar">' +
            '<table><thead><tr><button v-on:click="prev"><<</button>{{year}}年{{month}}月<button v-on:click="next">>></button></tr>'+
                '<tr><th><span>周天</span></th><th><span>周一</span></th>'+
                '<th><span>周二</span></th><th><span>周三</span></th>'+
                '<th><span>周四</span></th><th><span>周五</span></th><th><span>周六</span></th></tr></thead>'+
            '<tbody v-on:click="checkDay"><tr><td v-for="info in week1">{{info.day}}</td></tr>'+
                '<tr><td v-for="info in week2">{{info.day}}</td></tr>'+
                '<tr><td v-for="info in week3">{{info.day}}</td></tr>'+
                '<tr><td v-for="info in week4">{{info.day}}</td></tr>'+
                '<tr><td v-for="info in week5">{{info.day}}</td></tr>'+
                '<tr><td v-for="info in week6">{{info.day}}</td></tr>'+
            '<tbody></table></div>',
    days: '',
    week1: '',
    week2: '',
    week3: '',
    week4: '',
    week5: '',
    week6: '',
    data: function () {
        days = new Array();
        week1 = [{day:''},{day:''},{day:''},{day:''},{day:''},{day:''},{day:''}];
        week2 = [{day:''},{day:''},{day:''},{day:''},{day:''},{day:''},{day:''}];
        week3 = [{day:''},{day:''},{day:''},{day:''},{day:''},{day:''},{day:''}];
        week4 = [{day:''},{day:''},{day:''},{day:''},{day:''},{day:''},{day:''}];
        week5 = [{day:''},{day:''},{day:''},{day:''},{day:''},{day:''},{day:''}];
        week6 = [{day:''},{day:''},{day:''},{day:''},{day:''},{day:''},{day:''}];
        this.init_calendar();
        return {
            week1: week1,
            week2: week2,
            week3: week3,
            week4: week4,
            week5: week5,
            week6: week6,
        }
    },
    methods: {
        init_calendar: function(){
            this.load_days();
            var num = 1;
            for(var i=0; i<days.length; i++){
                var week = days[i].week;
                if(week == 0 && i > 0) {
                    num ++;
                }
                switch (num) {
                    case 1:
                        week1[week].day=days[i].day;
                        break;
                    case 2:
                        week2[week].day=days[i].day;
                        break;
                    case 3:
                        week3[week].day=days[i].day;
                        break;
                    case 4:
                        week4[week].day=days[i].day;
                        break;
                    case 5:
                        week5[week].day=days[i].day;
                        break;
                    case 6:
                        week6[week].day=days[i].day;
                        break;
                }
            }
        },
        load_days:function(){
            for (var i=0; i<7; i++){
                week1[i].day = '';
                week2[i].day = '';
                week3[i].day = '';
                week4[i].day = '';
                week5[i].day = '';
                week6[i].day = '';
            }
            var year = parseInt(this.year);
            var month = parseInt(this.month);
            var bean = {year:year, month:month};
            //发送ajax请求
            var beans = {year:year, month:month};
            //var url = "http://opendata.baidu.com/api.php?query=2020-10&resource_id=6018&format=json";
            //var url = "http://127.0.0.1:8081/springboot/calendar/querylist?year=" + year + "&month=" + month;
            var url = "/springboot/calendar/querylist";
            $.ajax({
                type: "post",
                async: false,//同步，异步
                url: url, //请求的服务端地址
                data: beans,
                dataType: "json",
                success:function(data){
                    if(null != data && "0" == data.code){
                        days.length = 0;
                        var list = data.list;
                        for(var i in list){
                            var day = list[i].workday;
                            var week = list[i].workweek;
                            var bean = {day: day, week: week};
                            days.push(bean);
                        }
                    }else{
                        days.length = 0;
                    }
                },
                error:function(i, s, e){
                    flag = false;
                    alert('error'); //错误的处理
                }
            });
        },
        prev: function (arg) {
            var year = parseInt(this.year);
            var month = parseInt(this.month);
            if (month > 1){
                this.month = month - 1;
            }else{
                this.year = year - 1;
                this.month = 12;
            }
            this.$parent.month = this.month;
            this.$parent.year = this.year;
            this.init_calendar();
        },
        next: function (arg) {
            var year = parseInt(this.year);
            var month = parseInt(this.month);
            if (month < 12){
                this.month = month + 1;
            }else{
                this.year = year + 1;
                this.month = 1;
            }
            this.$parent.month = this.month;
            this.$parent.year = this.year;
            this.init_calendar();
        },
        checkDay: function (arg) {
            var target = arg.target;
            alert(arg.target.innerText);
        }
    },
})

Vue.component('vue-tree2', {
    props: ['userid'],
    template: '<ol class="tree">'+
        '<li v-for="site in sites">'+
            '<input v-if="box" type="checkbox" id="folder1"><label v-on:click="extend_nodes" class="folderOne" :nid="site.id">{{site.name}}</label>'+
            '<ol v-for="note in site.notes"><li class="file folderTwo"><label>{{note.name}}</label></li></ol>'+
        '<li>'+
    '<ol>',
    data: function () {
        var sites = this.init_nodes();
        return {sites: sites,box: false}
    },
    methods: {
        init_nodes: function(){
            debugger
            //var nodes = [{name: "root1", notes:[{"name":"test1"},{"name":"test2"}]},{name: "root2"},{name: "root3"},{name: "root4"},{name: "root5"}];
            var nodes = new Array();;
            var userid = this.userid;
            var url = "/springboot/module/queryByUserid";
            $.ajax({
                type: "post",
                async: false,//同步，异步
                url: url, //请求的服务端地址
                data: {userid: this.userid},
                dataType: "json",
                success:function(data){
                    $(data).each(function(i, item){
                        var modulename = item.modulename;
                        var node = {name: modulename, id:1};
                        nodes.push(node);
                    })
                    console.log(data);
                },
                error:function(i, s, e){
                    flag = false;
                    alert('error'); //错误的处理
                }
            });
            return nodes;
        },
        extend_nodes: function(arg){
            debugger
            var ols = arg.target.parentNode.getElementsByTagName("ol");
            Array.prototype.slice.call(ols).forEach(function(item, i){
                debugger
                var e = item.style.display;
                if("block" == e || "" == e){
                    item.style.display = "none";
                }else{
                    item.style.display = "block";
                }
            })
        }
    }
})



