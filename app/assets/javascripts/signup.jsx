var ValidationError = React.createClass({
   render: function() {
       if(this.props.error == null) {
           return <div></div>;
       }
       return (
         <span className="help-block with-errors">
             <ul className="list-unstyled">
                 <li>{this.props.error}</li>
             </ul>
         </span>
       );
   }
});

var InputForm = React.createClass({
    render: function () {
        var className = "form-group";
        if(this.props.error != null) {
            className += " has-feedback has-error";
        }
        return (
            <div className={className}>
                <label for={this.props.id} className="control-label">{this.props.label}</label>
                <input type={this.props.type} defaultValue={this.props.defaultValue} name={this.props.name}
                       id={this.props.id} className="form-control" placeholder={this.props.placeholder}/>
                <ValidationError error={this.props.error} />
            </div>
        )
    }
});

var SignUpForm = React.createClass({
    formId: "signup",
    getInitialState: function () {
        return {
            errors: {}
        };
    },
    saveUser: function (e) {
        e.preventDefault();
        self = this;
        $.ajax({
            type: "POST",
            url: self.props.postUrl,
            data: $("#" + this.formId).serialize(),
            dataType: "json",
            success: function (result) {
                location.href = self.props.redirectUrl;
            },
            error: function (xhr, status, err) {
                console.error(xhr);
                self.setState({
                   errors: JSON.parse(xhr["responseText"])
                });
            }
        });
    },
    render: function () {
        return (
            <div className="signup-form">
                <form id={this.formId}>
                    <InputForm id="email"
                               label="メールアドレス（必須）"
                               type="email"
                               defalutValue=""
                               name="email"
                               placeholder="***@gmail.com"
                               error={this.state.errors["email"]}/>

                    <InputForm id="password"
                               label="パスワード（必須）"
                               type="password"
                               defalutValue=""
                               name="password"
                               placeholder="********"
                               error={this.state.errors["password"]}/>


                    <InputForm id="userName"
                               label="ユーザ名（必須）"
                               type="text"
                               defalutValue=""
                               name="userName"
                               placeholder="テストユーザ"
                               error={this.state.errors["password"]}/>

                    <a onClick={this.saveUser} className="btn btn-primary btn-lg pull-right">送信</a>
                </form>
            </div>
        )
    }
});

var SignUp = React.createClass({
    render: function () {
        return (
            <div className="row">
                <div className="col-sm-4"></div>
                <div className="col-sm-4">
                    <h1>新規登録</h1>
                    <SignUpForm postUrl={this.props.postUrl}
                                redirectUrl={this.props.redirectUrl}/>
                </div>
                <div className="col-sm-4"></div>
            </div>
        )
    }
});

var signupContainer = document.getElementById("content");
var postUrl = signupContainer.getAttribute("postUrl");
var redirectUrl = signupContainer.getAttribute("redirectUrl");

ReactDOM.render(
    <SignUp postUrl={postUrl}
            redirectUrl={redirectUrl}/>,
    document.getElementById('content')
);