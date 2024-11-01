import Body from "@/containers/home/body";
import Footer from "@/containers/home/footer";
import Header from "@/containers/home/header";
import {
  GetServerSideProps,
  GetServerSidePropsContext,
  InferGetServerSidePropsType,
} from "next";

interface HomeProps {
  shelfId: string;
}

export default async function Home(
  props: InferGetServerSidePropsType<typeof getServerSideProps>,
) {
  return (
    <>
      <Header />
      <Body shelfId={props.shelfId} />
      <Footer />
    </>
  );
}

const getServerSideProps = (async (context: GetServerSidePropsContext) => {
  const { shelfId } = context.query;

  if (shelfId === undefined || Array.isArray(shelfId)) {
    const defaultShelf = await fetch("/api/shelf/default", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .catch((error) => {
        throw new Error(error);
      });

    return {
      props: {
        shelfId: defaultShelf,
      },
    };
  }

  return {
    props: {
      shelfId: shelfId,
    },
  };
}) satisfies GetServerSideProps<HomeProps>;
