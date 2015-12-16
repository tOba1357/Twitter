var GeneralValidationError = React.createClass({
   render: function() {
       if(this.props.error == null) {
           return(<div></div>);
       }
       return (
           <div className="alert alert-danger" role="alert">
               <span className="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
               <span classNAme="sr-only">Error:</span>
               {this.props.error}
           </div>
       );
   }
});

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

var Form = React.createClass({
    getInitialState: function () {
        return {
            errors: {}
        };
    },
    doPost: function (e) {
        e.preventDefault();
        self = this;
        $.ajax({
            type: "POST",
            url: self.props.postUrl,
            data: $("#" + self.props.formId).serialize(),
            dataType: "json",
            success: function (result) {
                console.log(result);
                location.href = self.props.redirectUrl;
            },
            error: function (xhr, status, err) {
                console.error(xhr);
                self.props.setErrors(JSON.parse(xhr["responseText"]));
            }
        });
    },
    render: function () {
        return (
            <div className="signup-form">
                <form id={this.props.formId}>
                    {this.props.children}
                    <a onClick={this.doPost} className="btn btn-primary pull-right">{this.props.buttonValue}</a>
                </form>
            </div>
        )
    }

});