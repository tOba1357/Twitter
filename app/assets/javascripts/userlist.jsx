var UserTableRow = React.createClass({
    getInitialState: function () {
        return {
            follow: this.props.user["follow"]
        };
    },
    follow: function (e) {
        e.preventDefault();
        self = this;
        $.ajax({
            type: "POST",
            url: this.props.followUrl,
            data: "id=" + this.props.user["id"],
            dataType: "json",
            success: function (result) {
                self.setState({
                    follow: true
                });
            },
            error: function (xhr, status, err) {
                console.error(xhr);
            }
        });
    },
    releaseFollow: function (e) {
        e.preventDefault();
        self = this;
        $.ajax({
            type: "POST",
            url: self.props.releaseFollowerUrl,
            data: "id=" + this.props.user["id"],
            dataType: "json",
            success: function (result) {
                self.setState({
                    follow: false
                });
            },
            error: function (xhr, status, err) {
                console.error(xhr);
            }
        });
    },
    getFollowButton: function () {
        if (this.state.follow) {
            return (<a onClick={this.releaseFollow} className="btn btn-danger btn-sm">フォロー解除</a>)
        } else {
            return (<a onClick={this.follow} className="btn btn-primary btn-sm">フォローする</a>)
        }
    },
    render: function () {
        return (
            <tr>
                <td>{this.props.user["id"]}</td>
                <td>{this.props.user["userName"]}</td>
                <td>{this.props.user["email"]}</td>
                <td>{this.getFollowButton()}</td>
            </tr>
        )
    }
});

var UserTable = React.createClass({
    getInitialState: function () {
        return {
            userList: null
        };
    },
    componentDidMount: function () {
        this.loadUserList();
    },
    loadUserList: function () {
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
    render: function () {
        self = this;
        if (this.state.userList == null) {
            return (<div></div>);
        }
        var userList = this.state.userList.map(function (user, index) {
            return (
                <UserTableRow key={index}
                                  user={user}
                                  followUrl={self.props.followUrl}
                                  releaseFollowerUrl={self.props.releaseFollowerUrl}/>
            )
        });
        return (
            <table className="table table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>ユーザー名</th>
                    <th>メールアドレス</th>
                    <th>フォロー</th>
                </tr>
                </thead>
                <tbody>
                {userList}
                </tbody>
            </table>
        )
    }
});

var UserList = React.createClass({
    render: function () {
        return (
            <div className="row">
                <div className="col-sm-3"></div>
                <div className="col-sm-6">
                    <h1>ユーザリスト</h1>
                    <UserTable getUrl={this.props.getUrl}
                               followUrl={followUrl}
                               releaseFollowerUrl={releaseFollowerUrl}/>
                </div>
                <div className="col-sm-3"></div>
            </div>
        )
    }
});

var userlistContainer = document.getElementById("content");
var getUrl = userlistContainer.getAttribute("getUrl");
var followUrl = userlistContainer.getAttribute("followUrl");
var releaseFollowerUrl = userlistContainer.getAttribute("releaseFollowerUrl");

ReactDOM.render(
    <UserList getUrl={getUrl}
              followUrl={followUrl}
              releaseFollowerUrl={releaseFollowerUrl}/>,
    document.getElementById('content')
);