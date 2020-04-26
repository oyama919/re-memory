
import React, { Component } from 'react';
import axios from 'axios';
import './Login.css';
import Email from './email';
let params = new URLSearchParams();

const url = 'http://localhost:9000/auth/login';
params.append('email', 'test1@test.com');
params.append('password', 'test');

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: 'でふぉると'
    };
  }
  render() {
    this.set()
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
  async set() {
    const data = await axios.post(url, params).data
    console.log(data)
    // this.setState({
    //   name: 'setYuki'
    // });
  }
}
  
export default Login;
