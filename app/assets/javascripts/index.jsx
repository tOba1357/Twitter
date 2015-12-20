var TweetNode = React.createClass({
    retweet:function(e) {
        e.preventDefault();
        $.ajax({
            type: "POST",
            url: this.props.retweetUrl,
            data: "tweetId=" + this.props.tweet["id"],
            dataType: "json",
            success: function (result) {
            },
            error: function (xhr, status, err) {
                console.error(xhr);
            }
        });
    },
    getRetweetButton: function() {
        if(this.props.tweet["doRetweet"]) {
            return (
                <div className="retweet" onClick={this.retweet}>リツイート</div>
            );
        } else {
            return (<div></div>);
        }
    },
    render: function () {
        return (
            <div className="tweet">
                <dl>
                    <dt>{this.props.tweet["author"]}</dt>
                    <dd>
                        {this.props.tweet["content"]}
                        <div>
                        {this.getRetweetButton()}
                        <div className="tweet-date">{this.props.tweet["createDate"]}</div>
                        </div>
                    </dd>
                </dl>
            </div>
        );
    }
});

var TweetList = React.createClass({
    getInitialState: function () {
        return {
            tweets: [],
            update: false,
            loadedTweetFirstIndex: null,
            loadedTweetLastIndex: null
        };
    },
    componentDidMount: function () {
        this.loadTweets();
        setInterval(this.loadTweets, 2000);
    },
    loadTweets: function() {
        self = this;
        var parametars = "size=10";
        if(self.state.loadedTweetLastIndex != null) {
            parametars += "&from=" + this.state.loadedTweetLastIndex;
        }
        $.ajax({
            type: "GET",
            url: self.props.getUrl,
            data: parametars,
            dataType: "json",
            success: function (result) {
                console.log(result);
                self.setState({
                    tweets: result["tweetViewList"],
                    loadedTweetFirstIndex: Math.min(result["firstIndex"], self.state.loadedTweetLastIndex),
                    loadedTweetLastIndex: Math.max(result["lastIndex"], self.state.loadedTweetLastIndex),
                    update: true
                });
            },
            error: function (xhr, status, err) {
                console.error(xhr);
            }
        });
    },
    render: function () {
        self = this;
        var tweetNodes = this.state.tweets.map(function (tweet, key) {
            return (
                <TweetNode tweet={tweet} key={key} retweetUrl={self.props.retweetUrl} />
            );
        });
        return (
            <div className="tweetList">
                {tweetNodes}

            </div>
        );
    }
});

var TweetForm = React.createClass({
    formId: "tweet",
    saveTweet: function (e) {
        e.preventDefault();
        self = this;
        $.ajax({
            type: "POST",
            url: self.props.postUrl,
            data: $("#" + this.formId).serialize(),
            dataType: "json",
            success: function (result) {
                document.getElementById("tweet-content").value = "";
            },
            error: function (xhr, status, err) {
                console.error(xhr);
            }
        });
    },
    render: function () {
        return (
            <form id={this.formId}>
                <input type="hidden" name="userId" value="1"/>
                <p><textarea className="form-control" rows="4" placeholder="ツイート内容" name="content" id="tweet-content"></textarea></p>
                <a className="btn btn-primary pull-right" onClick={this.saveTweet}>送信</a>
            </form>
        )
    }
});

var TweetBox = React.createClass({
    render: function () {
        return (
            <div className="row">
                <div className="col-sm-4"></div>
                <div className="col-sm-4">
                    <h1>Twitter</h1>
                    <TweetForm postUrl={this.props.postUrl}/>
                    <TweetList getUrl={this.props.getUrl} retweetUrl={this.props.retweetUrl}/>
                </div>
                <div className="col-sm-4"></div>
            </div>
        )
    }
});

var indexContainer = document.getElementById("content");
var getUrl = indexContainer.getAttribute("getUrl");
var postUrl = indexContainer.getAttribute("postUrl");
var retweetUrl = indexContainer.getAttribute("retweetUrl");

ReactDOM.render(
    <TweetBox getUrl={getUrl}
              postUrl={postUrl}
              retweetUrl={retweetUrl}/>,
    document.getElementById('content')
);