import {Route, Switch} from "react-router";
import App from "../App";
import * as React from "react";

const routes = (
    <div>
        <Switch>
            <Route exact={true} path="/" component={App}/>
            <Route path="/**" component={App} />
        </Switch>
    </div>
)

export default routes;