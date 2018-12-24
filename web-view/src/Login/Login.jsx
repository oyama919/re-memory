
import React, { Component } from 'react';
import axios from 'axios';
import './Login.css';
import Email from './email';
let params = new URLSearchParams();

const API_GET = 'http://localhost:9000/auth/login';
params.append('email', 'test1@test.com');
params.append('password', 'test');

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: ''
    };
  }
  render() {
    axios
    .post(API_GET, params )
    .then((results) => {
      console.log(results);
      this.setState({
        name: 'setYuki'
      });
    })
    .catch(e => {
      console.log(e);
    });
    return (
      <div className="App">
        <header className="App-header">
          <Email name={this.state.name}/>
          <p>
          Edit <code>src/App.js</code> and save to reload.
          </p>
        </header>
      </div>
    );
  }
}
  
export default Login;
