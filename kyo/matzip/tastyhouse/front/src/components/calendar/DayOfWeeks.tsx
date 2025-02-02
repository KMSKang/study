import { colors } from '@/constants';
import useThemeStore from '@/store/useThemeStore';
import { ThemeMode } from '@/types';
import React from 'react';
import { Dimensions, StyleSheet, Text, View } from 'react-native';

function DayOfWeeks() {
    const { theme } = useThemeStore();
    const styles = styling(theme);
    
    return (
        <View style={styles.container}>
            {['일', '월', '화', '수', '목', '금', '토'].map((dayOfWeek, i) => {
                return (
                    <View key={i} style={styles.item}>
                        <Text
                            style={[
                                styles.text,
                                dayOfWeek === '토' && styles.saturdayText,
                                dayOfWeek === '일' && styles.sundayText,
                            ]}>
                            {dayOfWeek}
                        </Text>
                    </View>
                );
            })}
        </View>
    );
}

//const styles = StyleSheet.create({
const styling = (theme: ThemeMode) =>
    StyleSheet.create({
        container: {
            flexDirection: 'row',
            marginBottom: 5,
        },
        item: {
            width: Dimensions.get('window').width / 7,
            alignItems: 'center',
        },
        text: {
            fontSize: 12,
            //color: colors.BLACK,
            color: colors[theme].BLACK,
        },
        saturdayText: {
            //color: colors.BLUE_500,
            color: colors[theme].BLUE_500,
        },
        sundayText: {
            //color: colors.RED_500,
            color: colors[theme].RED_500,
        },
    });

export default DayOfWeeks;