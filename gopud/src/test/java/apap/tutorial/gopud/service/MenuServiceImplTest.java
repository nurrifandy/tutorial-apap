package apap.tutorial.gopud.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import apap.tutorial.gopud.model.MenuModel;
import apap.tutorial.gopud.repository.MenuDb;

@RunWith(MockitoJUnitRunner.class)
public class MenuServiceImplTest{

    @InjectMocks
    MenuService menuService = new MenuServiceImpl();

    @Mock
    MenuDb menuDb;

    @Test
    public void whenAddValidMenuItShouldCallMenuRepositorySave(){
        MenuModel newMenu = new MenuModel();
        newMenu.setNama("burger");
        newMenu.setHarga(BigInteger.valueOf(31000));
        newMenu.setDurasiMasak(15);
        newMenu.setDeskripsi("Junk Food Enak");
        menuService.addMenu(newMenu);
        verify(menuDb, times(1)).save(newMenu);
    }

    @Test
    public void whenGetMenuListByIdRestoranCalledItShouldReturnAllMenuInThatRestoran(){
        List<MenuModel> allMenuByIdInDatabase = new ArrayList<>();
        long idRestoran = 3;
        for (int loopTimes = 3; loopTimes > 0; loopTimes--) {
            allMenuByIdInDatabase.add(new MenuModel());
        }
        when(menuService.findAllMenuByIdRestoran(idRestoran)).thenReturn(allMenuByIdInDatabase);
        List<MenuModel> dataFromServiceCall = menuService.findAllMenuByIdRestoran(idRestoran);
        assertEquals(3, dataFromServiceCall.size());
        verify(menuDb, times(1)).findByRestoranIdRestoran(idRestoran);
    }

    @Test
    public void whenGetMenuByIdCalledByExistingDataItShouldReturnCorrectData() {
        MenuModel returnedData = new MenuModel();
        returnedData.setNama("Ayam Panggang");
        returnedData.setHarga(BigInteger.valueOf(15000));
        returnedData.setId((long)1);
        returnedData.setDurasiMasak(10);
        returnedData.setDeskripsi("Mantap surantap");

        when(menuService.getMenuByIdMenu(1L)).thenReturn(Optional.of(returnedData));
        
        Optional<MenuModel> dataFromServiceCall =
        menuService.getMenuByIdMenu(1L);
        verify(menuDb, times(1)).findById(1L);
        assertTrue(dataFromServiceCall.isPresent());
        MenuModel dataFromOptional = dataFromServiceCall.get();
        assertEquals("Ayam Panggang", dataFromOptional.getNama());
        assertEquals(BigInteger.valueOf(15000), dataFromOptional.getHarga());
        assertEquals(Long.valueOf(1), dataFromOptional.getId());
        assertEquals(Integer.valueOf(10), dataFromOptional.getDurasiMasak());
        assertEquals("Mantap surantap", dataFromOptional.getDeskripsi());
    }

    @Test
    public void whenChangeRestoranCalledItShouldChangeRestoranData() {
        MenuModel updatedData = new MenuModel();
        
        updatedData.setNama("Nasi Gila");
        updatedData.setDeskripsi("Dummy enak");
        updatedData.setId((long)1);
        updatedData.setHarga(BigInteger.valueOf(12000));
        updatedData.setDurasiMasak(10);
        
        when(menuDb.findById(1L)).thenReturn(Optional.of(updatedData));
        
        when(menuService.changeMenu(updatedData)).thenReturn(updatedData);
        
        MenuModel dataFromServiceCall = menuService.changeMenu(updatedData);

        assertEquals("Nasi Gila", dataFromServiceCall.getNama());
        assertEquals("Dummy enak", dataFromServiceCall.getDeskripsi());
        assertEquals(Long.valueOf(1), dataFromServiceCall.getId());
        assertEquals(BigInteger.valueOf(12000), dataFromServiceCall.getHarga());
        assertEquals(Integer.valueOf(10), dataFromServiceCall.getDurasiMasak());
        verify(menuDb, times(1)).save(updatedData);
    }

    @Test(expected = NullPointerException.class)
    public void whenChangeMenuCalledItShouldNullData(){
        MenuModel nullMenu = null;
        nullMenu.getNama();
        nullMenu.getHarga();
        nullMenu.getDeskripsi();
        nullMenu.getDurasiMasak();
    }

    @Test
    public void testForDeletingMenu(){
        MenuModel newMenu = new MenuModel();
        newMenu.setNama("burger");
        newMenu.setHarga(BigInteger.valueOf(31000));
        newMenu.setDurasiMasak(15);
        newMenu.setDeskripsi("Junk Food Enak");
        menuService.deleteMenu(newMenu);
        verify(menuDb, times(1)).delete(newMenu);
    }
}