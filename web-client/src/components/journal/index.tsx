"use client";

export interface JournalProps {
  id: string;
  title: string;
}

export default function Journal(props: JournalProps) {
  const { id, title } = props;

  return (
    <div key={id} className="w-64 h-72 border border-gray-400">
      {id}
      <br />
      {title}
    </div>
  );
}
