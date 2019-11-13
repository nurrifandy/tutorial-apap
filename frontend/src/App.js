import React from 'react';
import logo from './logo.svg';
import './App.css';
import List from "./components/List";
import dummyItems from "./items.json";

export default class App extends React.Component{
  constructor(props){
    super(props);
    this.state={
      favItems:[]
    }
  }
  handleItemClick=item =>{
    const newItems = [...this.state.favItems];
    const newItem = {...item};

    const targetInd = newItems.findIndex(it => it.id === newItem.id);
    if(targetInd<0) newItems.push(newItem);
    else newItems.splice(targetInd, 1);

    this.setState({favItems:newItems});
  }

  handleItemFix=item =>{
    const newItems = [...this.state.favItems];
    const newItem = {...item};

    const targetInd = newItems.findIndex(it => it.id === newItem.id);
    if(targetInd<0) newItems.push(newItem);
    this.setState({favItems:newItems});
  }

  render(){
    const {favItems}=this.state;
    return (
      <div className="container-fluid">
        <h1 className="text-center">
          Welcome!
          <small>Class-Based</small>
        </h1>
        <div className="container pt-3">
          <div className="row">
            <div className="col-sm">
              <List
              title="Our Menu"
              items={dummyItems}
              onItemClick={this.handleItemFix}/>
            </div>
            <div className="col-sm">
            <input type="checkbox"/>Show Favorite
            <List
              title="My Favorite"
              items={favItems}
              onItemClick={this.handleItemClick}/>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

/** 
function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
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

export default App;
*/