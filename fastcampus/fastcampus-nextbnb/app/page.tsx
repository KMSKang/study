'use client'

import {RoomType} from "@/interface";
import CategoryList from "@/components/CategoryList";
import {GridLayout, RoomItem} from "@/components/RoomList";
import Loader, {LoaderGrid} from '@/components/Loader'
import React, {useEffect, useRef} from "react";
import useIntersectionObserver from "@/hooks/useIntersectionObserver";
import {useInfiniteQuery} from "react-query";
import axios from "axios";

export default function Home() {
	// const fetchRoom = async () => {
	// 	const data = await fetch('/api/rooms')
	// 	return data.json()
	// }
	//
	// const { data, isError, isLoading } = useQuery('rooms', fetchRoom)
	//
	// if (isLoading) {
	// 	return <Loader className="mt-60 mb-40" />
	// }
	const ref = useRef<HTMLDivElement | null>(null)
	const pageRef = useIntersectionObserver(ref, {})
	const isPageEnd = !!pageRef?.isIntersecting

	const fetchRooms = async ({pageParam = 1}) => {
		const {data} = await axios('/api/rooms?page=' + pageParam, {
			params: {
				limit: 12,
				page: pageParam,
			},
		})

		return data
	}

	const {
		data: rooms,
		isFetching,
		fetchNextPage,
		isFetchingNextPage,
		hasNextPage,
		isError,
		isLoading,
	} = useInfiniteQuery('rooms', fetchRooms, {
		getNextPageParam: (lastPage, pages) =>
				lastPage?.data?.length > 0 ? lastPage.page + 1 : undefined,
	})

	useEffect(() => {
		let timerId: NodeJS.Timeout | undefined

		if (isPageEnd && hasNextPage) {
			timerId = setTimeout(() => {
				fetchNextPage()
			}, 500)
		}
	}, [fetchNextPage, hasNextPage, isPageEnd])

	return (
			<>
				<CategoryList/>
				<GridLayout>
					{/*{data?.map((room: RoomType) => <RoomItem room={room} key={room.id}/>)}*/}
					{isLoading || isFetching ? (
							<LoaderGrid/>
					) : (
							rooms?.pages?.map((page, index) => (
									<React.Fragment key={index}>
										{page?.data?.map((room: RoomType) => (
												<RoomItem room={room} key={room.id}/>
										))}
									</React.Fragment>
							))
					)}
				</GridLayout>
				{(isFetching || hasNextPage || isFetchingNextPage) && <Loader/>}
				<div className="w-full touch-none h-10 mb-10" ref={ref}/>
			</>
	);
}
