package apap.tutorial.gopud.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigInteger;

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
}