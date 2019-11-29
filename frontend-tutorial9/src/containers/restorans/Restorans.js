import React, {Component} from 'react';
import Restoran from './Restoran';
import classes from "./Restorans.module.css";
import axios from './axios-restoran';
import Modal from '../../components/UI/Modal/Modal';
import Button from '../../components/UI/Button/Button';

class Restorans extends Component{
    constructor(props){
        super(props);
        this.state={
            restorans:[],
            isCreate:false,
            isLoading:true,
            isEdit:false,
            nama: "",
            alamat:"",
            nomorTelepon:"",
            rating:"",
            filteredRestoran:[],
            search:"",
            isSearch:true,
            currentPage : 1,
            restoransPerPage : 5
        }
        this.handleClick = this.handleClick.bind(this);
    }

    componentDidMount(){
        //console.log("componentDidMount()");
        this.loadRestorans();
    }

    addRestoranHandler = () =>{
        this.setState({ isCreate:true, nama:"",alamat:"",nomorTelepon:"", rating:""});
    }

    canceledHandler = () => {
        this.setState({ isCreate:false, isEdit:false});
    }

    handleClick(event) {
        this.setState({
          currentPage: Number(event.target.id)
        });
      }

    handleInputChange = event => {
        const search = event.target.value;
        this.setState({isSearch : false})
        this.setState(prevState => {
          const filteredRestoran = prevState.restorans.filter(restoran => {
            return restoran.nama.toLowerCase().startsWith(search.toLowerCase());
          });
    
          return {
            search,
            filteredRestoran
          };
        });
      };

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

    changeHandler = event => {
        const{name, value}=event.target;
        this.setState({[name]:value});
    };

    async deleteRestoranHandler(restoranId){
        await axios.delete('/restoran/${restoranId}');
        await this.loadRestorans();
    }

    editRestoranHandler(restoran){
        this.setState({
            isEdit:true,
            idRestoran:restoran.idRestoran,
            nama:restoran.nama,
            nomorTelepon:restoran.nomorTelepon,
            rating:restoran.rating,
            alamat:restoran.alamat
        })
    }

    submitEditRestoranHandler=event=>{
        console.log("editing")
        event.preventDefault();
        this.setState({ isLoading:true });
        this.editRestoran();
        this.canceledHandler();
    };

    async editRestoran(){
        const restoranToEdit = {
            idRestoran:this.state.idRestoran,
            nama:this.state.nama,
            alamat:this.state.alamat,
            nomorTelepon:this.state.nomorTelepon,
            rating:this.state.rating
        };
        await axios.put("/restoran/" + this.state.idRestoran,restoranToEdit);
        await this.loadRestorans();
        this.canceledHandler();
    }
    submitAddRestoranHandler = event => {
        event.preventDefault();
        this.setState({isLoading:true});
        this.addRestoran();
        this.canceledHandler();
    }

    async addRestoran(){
        const restoranToAdd = {
            nama :this.state.nama,
            alamat:this.state.alamat,
            nomorTelepon:this.state.nomorTelepon,
            rating:this.state.rating
        };
        await axios.post("/restoran", restoranToAdd);
        await this.loadRestorans();
    }

    shouldComponentUpdate(nextProps, nextState){
        console.log("shouldComponentUpdate()");
        return true;
    }

    loadingHandler=()=>{
        const currentIsLoading = this.state.isLoading;
        this.setState({isLoading: !(currentIsLoading)});
        console.log(this.state.isLoading);
    }

    renderForm(){
        const {isEdit}  = this.state;
        return(
            <form>
                <input
                className={classes.Input}
                name='nama'
                type='text'
                placeholder='Nama'
                value={this.state.nama}
                onChange={this.changeHandler}
                />
                <input
                className={classes.Input}
                name='nomorTelepon'
                type='number'
                placeholder='Nomor Telepon'
                value={this.state.nomorTelepon}
                onChange={this.changeHandler}
                />
                <input
                className={classes.TextArea}
                name='alamat'
                type='text'
                placeholder='Alamat'
                value={this.state.alamat}
                onChange={this.changeHandler}
                />
                <input
                className={classes.Input}
                name='rating'
                type='number'
                placeholder='Rating'
                value={this.state.rating}
                onChange={this.changeHandler}
                />
                <Button btnType='Succes' onClick={isEdit ? this.submitEditRestoranHandler:this.submitAddRestoranHandler}>
                    Submit
                </Button>
                <Button btnType='Danger' onClick={this.canceledHandler}>
                    Cancel
                </Button>
                
            </form>
        )
    }

    render(){
        console.log("render()");
        const { isSearch } = this.state;
        const { restorans, currentPage, restoransPerPage } = this.state;

        // Logic for displaying todos
        const indexOfLastRestoran = currentPage * restoransPerPage;
        const indexOfFirstRestoran = indexOfLastRestoran - restoransPerPage;
        const currentRestorans = restorans.slice(indexOfFirstRestoran, indexOfLastRestoran);

        // Logic for displaying page numbers
        const pageNumbers = [];
        for (let i = 1; i <= Math.ceil(restorans.length / restoransPerPage); i++) {
        pageNumbers.push(i);
        }
        const renderPageNumbers = pageNumbers.map(number => {
            return (
                <button
                key={number}
                id={number}
                onClick={this.handleClick}
                >
                {number}
                </button>
            );
            });

        return(
            <React.Fragment>
                <Modal show={this.state.isCreate || this.state.isEdit}
                modalClosed={this.canceledHandler}>
                    {this.renderForm()}
                </Modal>
                <div className={classes.Title}>All Restorans</div>
                <div className={classes.ButtonLayout}>
                    <button className={classes.AddRestoranButton}
                    onClick={this.addRestoranHandler}>
                        + Add New Restoran
                    </button>
                </div>
                <div>
                    <form>
                        <input
                        className={classes.searchForm}
                        placeholder="find restoran . . ."
                        value={this.state.search}
                        onChange={this.handleInputChange} />
                    </form>
                    <div>{this.state.filteredRestoran.map(restoran => 
                        <Restoran 
                        key = {restoran.id}
                        nama= {restoran.nama}
                        alamat = {restoran.alamat}
                        nomorTelepon = {restoran.nomorTelepon}
                        edit = {()=> this.editRestoranHandler(restoran)}
                        delete = {()=>this.deleteRestoranHandler(restoran.idRestoran)}
                        />
                        )}
                        </div>
                </div>
                <button onClick={this.loadingHandler}>changeState</button>
                <div className={classes.Restorans}>
                    {this.state.restorans && isSearch &&
                    currentRestorans.map(restoran =>
                        <Restoran 
                        key = {restoran.id}
                        nama= {restoran.nama}
                        alamat = {restoran.alamat}
                        nomorTelepon = {restoran.nomorTelepon}
                        edit = {()=> this.editRestoranHandler(restoran)}
                        delete = {()=>this.deleteRestoranHandler(restoran.idRestoran)}
                        />
                        )}
                </div>
                {renderPageNumbers}
        
            </React.Fragment>
        )
    }
}

export default Restorans;