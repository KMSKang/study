import BookItem from "@/components/book-item";
import style from "./page.module.css";
import {BookData} from "@/types";
import {Metadata} from "next";

async function AllBooks() {
	// await delay(1000);

	const response = await fetch(
			`${process.env.NEXT_PUBLIC_API_SERVER_URL}/book`,
			{cache: "force-cache"}
	);
	if (!response.ok) {
		return <div>오류가 발생했습니다...</div>;
	}

	const allBooks: BookData[] = await response.json();

	return (
			<div>
				{allBooks.map((book) => (
						<BookItem key={book.id} {...book} />
				))}
			</div>
	)
}

async function RecoBooks() {
	// await delay(1000);

	const response = await fetch(
			`${process.env.NEXT_PUBLIC_API_SERVER_URL}/book/random`,
			// { next: { revalidate: 3 } }
	);
	if (!response.ok) {
		return <div>오류가 발생했습니다...</div>;
	}

	const recoBooks: BookData[] = await response.json();

	return (
			<div>
				{recoBooks.map((book) => (
						<BookItem key={book.id} {...book} />
				))}
			</div>
	)
}

// export const dynamic = "force-dynamic";

export const metadata: Metadata = {
	title: "한입 북스",
	description: "한입 북스에 등록된 도서를 만나보세요",
	openGraph: {
		title: "한입 북스",
		description: "한입 북스에 등록된 도서를 만나보세요",
		images: ['/thumbnail.png'],
	}
}

// export default async function Home() {
export default function Home() {
	return (
			<div className={style.container}>
				<section>
					<h3>지금 추천하는 도서</h3>
					{/*<Suspense fallback={<BookListSkeleton count={3}/>}>*/}
						<RecoBooks/>
					{/*</Suspense>*/}
				</section>
				<section>
					<h3>등록된 모든 도서</h3>
					{/*<Suspense fallback={<BookListSkeleton count={10}/>}>*/}
						<AllBooks/>
					{/*</Suspense>*/}
				</section>
			</div>
	);
}
