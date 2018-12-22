
import React, { Component } from 'react';
import axios from 'axios';
import './App.css';
import Greeting from './greeting';

const API_GET = 'https://api.github.com';

class App extends Component {
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
      console.log(results);
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
      <Greeting name={this.state.name}/>
      <p>
      Edit <code>src/App.js</code> and save to reload.
      </p>
      </header>
      </div>
    );
  }
}
  
export default App;
