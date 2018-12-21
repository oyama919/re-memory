import React, { Component } from 'react';
import axios from 'axios';
import './App.css';

const API_GET = 'https://api.github.com';

class App extends Component {
  render() {

    axios
    .get(API_GET, {})
    .then((results) => {
      console.log(results);
      }
    )
    .catch(() => {
      console.log('通信に失敗しました');
    });

    return (
      <div className="App">
        <header className="App-header">
          <p>
            Edit <code>src/App.js</code> and save to reload.
          </p>
          <a
            className="App-link"
            href="https://reactjs.org"
            target="_blank"
            rel="noopener noreferrer"
          >
            Learn React
          </a>
        </header>
      </div>
    );
  }
}

export default App;
