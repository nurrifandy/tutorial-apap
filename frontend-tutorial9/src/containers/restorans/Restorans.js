import React, {Component} from 'react';
import Restoran from './Restoran';
import classes from "./Restorans.module.css";
import axios from './axios-restoran';

class Restorans extends Component{
    constructor(props){
        super(props);
        this.state={
            restorans:
            [
                
            ],
            isLoading:true,
        }
    }

    componentDidMount(){
        this.loadRestorans();
    }

    loadRestorans = async() =>  {
        const fetchedRestorans = [];
        const response = await axios.get("/restorans");
        for(let key in response.data){
            fetchedRestorans.push({
                ...response.data[key]
            });
        }

        this.setState({
            restorans:fetchedRestorans
        });
    };

    // shouldComponentUpdate(nextProps, nextState){
    //     console.log("shouldComponentUpdate()");
    //     return true;
    // }

    // loadingHandler=()=>{
    //     const currentIsLoading = this.state.isLoading;
    //     this.setState({isLoading: !(currentIsLoading)});
    //     console.log(this.state.isLoading);
    // }
    render(){
        console.log("render()");
        return(
            <React.Fragment>
                <div className={classes.Title}>All Restorans</div>
                <div className={classes.ButtonLayout}>
                    <button className={classes.AddRestoranButton}
                    onClick={this.addRestoranHandles}>
                        + Add New Restoran
                    </button>
                </div>
                <div className={classes.Restorans}>
                    {this.state.restorans && 
                    this.state.restorans.map(restoran =>
                        <Restoran 
                        key = {restoran.id}
                        nama= {restoran.nama}
                        alamat = {restoran.alamat}
                        nomorTelepon = {restoran.nomorTelepon}
                        />
                        )}
                </div>
                {/* <button onClick={this.loadingHandler}>changeState</button> */}
            </React.Fragment>
        )
    }
}

export default Restorans;