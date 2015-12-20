var UserTable = React.createClass({
    getInitialState: function () {
        return {
            userList: null
        };
    },
    componentDidMount: function () {
        this.loadUserList();
    },
    loadUserList: function() {
        self = this;
        $.ajax({
            type: "GET",
            url: self.props.getUrl,
            dataType: "json",
            success: function (result) {
                self.setState({
                    userList: result["userViewList"]
                });
            },
            error: function (xhr, status, err) {
                console.error(xhr);
            }
        });
    },
    render: function() {
        if(this.state.userList == null) {
            return(<div></div>);
        }
        var userList = this.state.userList.map(function(user, index) {
            return (
                <tr kye={index}>
                    <td>{index + 1}</td>
                    <td>{user["userName"]}</td>
                    <td>{user["email"]}</td>
                </tr>
            )
        });
       return(
           <table className="table table-hover">
               <thead><tr>
                   <th>#</th>
                   <th>ユーザー名</th>
                   <th>メールアドレス</th>
               </tr></thead>
               <tbody>
               {userList}
               </tbody>
           </table>
       )
   }
});

var UserList = React.createClass({
   render: function() {
       return(
           <div className="row">
               <div className="col-sm-4"></div>
               <div className="col-sm-4">
                   <h1>ユーザリスト</h1>
                   <UserTable getUrl={this.props.getUrl}/>
               </div>
               <div className="col-sm-4"></div>
           </div>
       )
   }
});


var userlistContainer = document.getElementById("content");
var getUrl = userlistContainer.getAttribute("getUrl");

ReactDOM.render(
    <UserList getUrl={getUrl}/>,
    document.getElementById('content')
);