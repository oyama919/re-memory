import React from 'react';

function App(props) {
  return (
    <div className="App">
      <p>{props.count}</p>
      <button onClick={()=>{props.countUpEvent()}}>Count UP</button>
    </div>
  );
}

export default App;
