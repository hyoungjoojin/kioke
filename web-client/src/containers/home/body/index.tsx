import { JournalProps } from "@/components/journal";
import Journals from "@/components/journals";
import { GetJournalResponse } from "@/types/api/journal";
import { GetShelfResponse } from "@/types/api/shelf";

interface BodyProps {
  shelfId: string;
}

export default async function Body(props: BodyProps) {
  const { shelfId } = props;
  const { name, journals: journalIds } = await getShelf(shelfId);

  const journals: JournalProps[] = (
    await Promise.all(
      journalIds.map((id) => {
        return getJournal(id);
      }),
    )
  ).map((journal) => {
    return {
      id: journal.id,
      title: journal.title,
    };
  });

  return (
    <main>
      <div className="flex justify-center">
        <h1 className="text-4xl">{name}</h1>
      </div>

      <Journals journals={journals} />
    </main>
  );
}

const getShelf = async (shelfId: string): Promise<GetShelfResponse> => {
  const response = await fetch(`${process.env.URL}/api/shelf/${shelfId}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (!response.ok) {
    throw new Error("Failed to get shelf");
  }

  return response.json();
};

const getJournal = async (journalId: string): Promise<GetJournalResponse> => {
  const response = await fetch(`${process.env.URL}/api/journal/${journalId}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (!response.ok) {
    throw new Error("Failed to get shelf");
  }

  return response.json();
};
