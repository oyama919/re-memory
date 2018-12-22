
import React, { Component } from 'react';
import axios from 'axios';
import './Login.css';
import Email from './email';

const API_GET = 'https://api.github.com';

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: ''
    };
  }
  render() {
    axios
    .get(API_GET, {})
    .then((results) => {
      // console.log(results);
      this.setState({
        name: 'setYuki'
      });
    }
    )
    .catch(() => {
      console.log('通信に失敗しました');
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
