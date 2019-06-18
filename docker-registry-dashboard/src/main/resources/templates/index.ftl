<!DOCTYPE html>
<html>


<link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<#setting datetime_format="yyyy-MM-dd HH:mm:ss">
<script src="https://cdn.staticfile.org/vue/2.2.2/vue.min.js"></script>
<body>
<div id="app">
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid" style=" margin-left:35%;">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Docker Registry</a>
            </div>
            <div>
                <form class="navbar-form navbar-left" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default">Search</button>
                </form>

            </div>
        </div>
    </nav>

    <div style="margin: 100px auto;  width: 900px;">

        <h4>Date:${.now}</h4>
        <div class="panel-default" style="padding-bottom:20px;">
            Result: ${result.message} , size: ${imagesSize}
            <button style="float: right;" id="fat-btn" class="btn btn-primary btn-sm" data-loading-text="Loading..."
                    type="button"> Refresh
            </button>

        </div>

        <div class="panel-group" id="accordion">
            <#list groupdsImages?keys as key>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <#if (key == "")>
                        <a data-toggle="collapse" data-parent="#accordion" href="#default">
                            ${key_index?if_exists+1}. default( ${groupdsImages[key]?size} )
                            <#else>
                            <a data-toggle="collapse" data-parent="#accordion" href="#${key}">
                                ${key_index?if_exists+1}. ${key} ( ${groupdsImages[key]?size})
                                </#if>
                            </a>
                    </h4>
                </div>
                <#if (key == "")>
                <div id="default" class="panel-collapse collapse in">
                    <#else>
                    <div id="${key}" class="panel-collapse collapse">
                        </#if>
                        <div class="panel-body">
                            <table class="table table-striped">
                                <caption>
                                    <button type="button" class="btn btn-warning">Delete Selected</button>
                                </caption>
                                <thead>
                                <tr>
                                    <th>#.</th>
                                    <th>No.</th>
                                    <th>Name</th>
                                    <th>Version</th>
                                    <th>Operator</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list groupdsImages[key] as image>
                                    <tr>
                                        <td><input class="checkbox" type="checkbox"></td>
                                        <td>${image_index?if_exists+1}</td>
                                        <td>${image.imageName}</td>
                                        <td>${image.imageTage}</td>
                                        <td>
                                            <button type="button" class="btn btn-default btn-sm "
                                                    v-on:click="deleteImage(  '${image.imageName}',  '${image.imageTage}')">
                                                Delete
                                            </button>
                                            <button class="btn btn-primary btn-sm "
                                                  v-on:click="detail( '${image.imageTage}') ">Detail</button>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>

                </#list>
            </div>


        </div>
    </div>

    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>

    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script>

        new Vue({
            el: '#app',
            data: {},
            methods: {
                deleteImage: function (imageName, imageTag) {
                    console.log("good1");
                    alert(imageName + ":" + imageTag);

                    var strs = new Array();
                    var imgName = "";
                    if(imageName.indexOf("/") != -1){
                        strs = imageName.split("/");
                        for(var i = 0; i< strs.length; i++){
                            if(i ==0){
                                imgName +=strs[i] +"___";
                            }else{
                                imgName +=strs[i];
                            }
                        }
                    }else{
                        imgName = imageName;
                    }
                    console.log(imgName +" ___ ");
                    $.ajax({
                        type:"DELETE",
                        url:"images/"+imgName+"/"+imageTag,
                        success:function(data){
                            alert(data)
                        },
                        error:function (data) {
                            alert("请求数据发送错误");

                        }
                    });
                },
                detail: function (imageTag) {
                    console.log("detail" + imageTag);
                    alert("detail no complate" + imageTag);
                }
            }
        });
        // $(function () {
        //     $(".btn").click(function () {
        //         $(this).button('loading').delay(1000).queue(function () {
        //             // $(this).button('reset');
        //             // $(this).dequeue();
        //         });
        //     });
        // });
    </script>

</body>


</html>