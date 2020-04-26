import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Content from './Login/Login.jsx';
import * as serviceWorker from './serviceWorker';

ReactDOM.render(<Content />, document.getElementById('root'));

serviceWorker.unregister();
