import { Dimensions, StyleSheet } from 'react-native';
import { createDrawerNavigator } from '@react-navigation/drawer';
import { NavigatorScreenParams, RouteProp } from '@react-navigation/native';
import MaterialIcons from 'react-native-vector-icons/MaterialIcons';

import CalendarHomeScreen from '@/screens/calendar/CalendarHomeScreen';
import MapStackNavigator, { MapStackParamList } from '../stack/MapStackNavigator';
import { colors, mainNavigations } from '@/constants';
import CustomDrawerContent from './CustomDrawerContent';
import FeedTabNavigator, { FeedTabParamList } from '../tab/FeedTabNavigator';
import FeedHomeHeaderLeft from '@/components/feed/FeedHomeHeaderLeft';
import SettingStackNavigator, { SettingStackParamList } from '../stack/SettingStackNavigator';
import { ThemeMode } from '@/types';
import useThemeStore from '@/store/useThemeStore';

export type MainDrawerParamList = {
    [mainNavigations.HOME]: NavigatorScreenParams<MapStackParamList>;
    [mainNavigations.FEED]: NavigatorScreenParams<FeedTabParamList>;
    [mainNavigations.CALENDAR]: undefined;
    [mainNavigations.SETTING]: NavigatorScreenParams<SettingStackParamList>;
};

const Drawer = createDrawerNavigator<MainDrawerParamList>();

function DrawerIcons(route: RouteProp<MainDrawerParamList>, focused: boolean) {
    let iconName = '';
    const { theme } = useThemeStore();
    const styles = styling(theme);

    switch (route.name) {
        case mainNavigations.HOME: {
            iconName = 'location-on';
            break;
        }
        case mainNavigations.FEED: {
            iconName = 'book';
            break;
        }
        case mainNavigations.CALENDAR: {
            iconName = 'event-note';
            break;
        }
        case mainNavigations.SETTING: {
            iconName = 'settings';
            break;
        }
    }

    return (
        <MaterialIcons
            name={iconName}
            //color={focused ? colors.BLACK : colors.GRAY_500}
            color={focused ? colors[theme].BLACK : colors[theme].GRAY_500}
            size={18}
        />
    );
}

function MainDrawerNavigator() {
    const { theme } = useThemeStore();
    const styles = styling(theme);
    
    return (
        <Drawer.Navigator
            drawerContent={CustomDrawerContent}
            screenOptions={({ route }) => ({
                headerShown: false,
                cardStyle: {
                    backgroundColor: colors[theme].WHITE,
                },
                headerStyle: {
                    shadowColor: 'gray',
                    backgroundColor: colors[theme].WHITE,
                },
                headerTitleStyle: {
                    fontSize: 15,
                    color: colors[theme].BLACK,
                },
                headerTintColor: colors[theme].BLACK,
                drawerType: 'front',
                drawerStyle: {
                    width: Dimensions.get('screen').width * 0.6,
                    //backgroundColor: colors.WHITE,
                    backgroundColor: colors[theme].WHITE,
                },
                //drawerActiveTintColor: colors.BLACK,
                drawerActiveTintColor: colors[theme].BLACK,
                //drawerInactiveTintColor: colors.GRAY_500,
                drawerInactiveTintColor: colors[theme].GRAY_500,
                //drawerActiveBackgroundColor: colors.PINK_200,
                drawerActiveBackgroundColor: colors[theme].PINK_200,
                //drawerInactiveBackgroundColor: colors.GRAY_100,
                drawerInactiveBackgroundColor: colors[theme].GRAY_100,
                drawerLabelStyle: {
                    fontWeight: '600',
                },
                drawerIcon: ({ focused }) => DrawerIcons(route, focused),
            })}>
            <Drawer.Screen
                name={mainNavigations.HOME}
                component={MapStackNavigator}
                options={{
                    title: '홈',
                    swipeEnabled: false,
                }}
            />
            <Drawer.Screen
                name={mainNavigations.FEED}
                component={FeedTabNavigator}
                options={{
                    title: '피드',
                }}
            />
            <Drawer.Screen
                name={mainNavigations.CALENDAR}
                component={CalendarHomeScreen}
                options={({ navigation }) => ({
                    title: '캘린더',
                    headerShown: true,
                    headerLeft: () => FeedHomeHeaderLeft(navigation),
                })}
            />
            <Drawer.Screen
                name={mainNavigations.SETTING}
                component={SettingStackNavigator}
                options={{
                    title: '설정',
                    drawerItemStyle: {
                        height: 0,
                    },
                }}
            />
        </Drawer.Navigator>
    );
}

const styling = (theme: ThemeMode) =>
    StyleSheet.create({
    });

export default MainDrawerNavigator;