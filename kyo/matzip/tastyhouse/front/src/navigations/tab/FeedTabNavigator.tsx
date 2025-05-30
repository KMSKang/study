import React from 'react';
import { StyleSheet } from 'react-native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import Ionicons from 'react-native-vector-icons/Ionicons';
import {
    RouteProp,
    getFocusedRouteNameFromRoute,
} from '@react-navigation/native';

import FeedStackNavigator from '../stack/FeedStackNavigator';
import FeedFavoriteScreen from '@/screens/feed/FeedFavoriteScreen';
import FeedHomeHeaderLeft from '@/components/feed/FeedHomeHeaderLeft';
import { colors, feedNavigations, feedTabNavigations } from '@/constants';
import FeedSearchScreen from '@/screens/feed/FeedSearchScreen';
import { ThemeMode } from '@/types';
import useThemeStore from '@/store/useThemeStore';

export type FeedTabParamList = {
    [feedTabNavigations.FEED_HOME]: {
        screen: typeof feedNavigations.FEED_DETAIL;
        params: { id: number };
        initial: boolean;
    };
    [feedTabNavigations.FEED_FAVORITE]: undefined;
    [feedTabNavigations.FEED_SEARCH]: undefined;
};

const Tab = createBottomTabNavigator<FeedTabParamList>();

function TabBarIcons(route: RouteProp<FeedTabParamList>, focused: boolean) {
    let iconName = '';
    const { theme } = useThemeStore();
    const styles = styling(theme);

    switch (route.name) {
        case feedTabNavigations.FEED_HOME: {
            iconName = focused ? 'reader' : 'reader-outline';
            break;
        }
        case feedTabNavigations.FEED_FAVORITE: {
            iconName = focused ? 'star' : 'star-outline';
            break;
        }
        case feedTabNavigations.FEED_SEARCH: {
            iconName = 'search';
            break;
        }
    }

    return (
        <Ionicons
            name={iconName}
            //color={focused ? colors.PINK_700 : colors.GRAY_500}
            color={focused ? colors[theme].PINK_700 : colors[theme].GRAY_500}
            size={25}
        />
    );
}

function FeedTabNavigator() {
    const { theme } = useThemeStore();
    const styles = styling(theme);
    
    return (
        <Tab.Navigator
            screenOptions={({ route }) => ({
                headerStyle: {
                    //backgroundColor: colors.WHITE,
                    backgroundColor: colors[theme].WHITE,
                    //shadowColor: colors.GRAY_200,
                    shadowColor: colors[theme].GRAY_200,
                },
                headerTitleStyle: {
                    fontSize: 15,
                },
                //headerTintColor: colors.BLACK,
                headerTintColor: colors[theme].BLACK,
                tabBarShowLabel: false,
                //tabBarActiveTintColor: colors.PINK_700,
                tabBarActiveTintColor: colors[theme].PINK_700,
                tabBarStyle: {
                    //backgroundColor: colors.WHITE,
                    backgroundColor: colors[theme].WHITE,
                    //borderTopColor: colors.GRAY_200,
                    borderTopColor: colors[theme].GRAY_200,
                    borderTopWidth: StyleSheet.hairlineWidth,
                },
                tabBarIcon: ({ focused }) => TabBarIcons(route, focused),
            })}>
            <Tab.Screen
                name={feedTabNavigations.FEED_HOME}
                component={FeedStackNavigator}
                options={({ route }) => ({
                    headerShown: false,
                    tabBarStyle: (tabRoute => {
                        const routeName =
                            getFocusedRouteNameFromRoute(tabRoute) ??
                            feedNavigations.FEED_HOME;

                        if (
                            routeName === feedNavigations.FEED_DETAIL ||
                            routeName === feedNavigations.EDIT_POST ||
                            routeName === feedNavigations.IMAGE_ZOOM
                        ) {
                            return { display: 'none' };
                        }

                        return {
                            //backgroundColor: colors.WHITE,
                            backgroundColor: colors[theme].WHITE,
                            //borderTopColor: colors.GRAY_200,
                            borderTopColor: colors[theme].GRAY_200,
                            borderTopWidth: StyleSheet.hairlineWidth,
                        };
                    })(route),
                })}
            />
            <Tab.Screen
                name={feedTabNavigations.FEED_FAVORITE}
                component={FeedFavoriteScreen}
                options={({ navigation }) => ({
                    headerTitle: '즐겨찾기',
                    headerLeft: () => FeedHomeHeaderLeft(navigation),
                })}
            />
            <Tab.Screen
                name={feedTabNavigations.FEED_SEARCH}
                component={FeedSearchScreen}
                options={({ navigation }) => ({
                    headerTitle: '검색',
                    headerShown: false,
                    headerLeft: () => FeedHomeHeaderLeft(navigation),
                })}
            />
        </Tab.Navigator>
    );
}

const styling = (theme: ThemeMode) =>
    StyleSheet.create({
    });

export default FeedTabNavigator;