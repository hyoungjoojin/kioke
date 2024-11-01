"use client";

import Journal, { JournalProps } from "@/components/journal";
import { MdArrowBackIos, MdArrowForwardIos } from "react-icons/md";

import styles from "./styles.module.css";
import { useEffect, useState } from "react";

interface JournalsProps {
  journals: JournalProps[];
}

export default function Journals({ journals }: JournalsProps) {
  const [displayedJournalsCount, setDisplayedJournalsCount] = useState(6);
  const pageCount = Math.floor(journals.length / displayedJournalsCount) + 1;

  const [page, setPage] = useState(0);

  useEffect(() => {
    const updateScreenSize = () => {
      setDisplayedJournalsCount((displayedJournalsCount) => {
        const updatedDisplayedJournalsCount = window.innerWidth > 768 ? 6 : 1;

        if (displayedJournalsCount !== updatedDisplayedJournalsCount) {
          setPage(0);
        }

        return updatedDisplayedJournalsCount;
      });
    };

    updateScreenSize();
    window.addEventListener("resize", updateScreenSize);
    return () => window.removeEventListener("resize", updateScreenSize);
  });

  return (
    <div className="flex justify-center items-center p-10">
      <MdArrowBackIos
        size={32}
        color="black"
        title="Previous Page"
        onClick={() => {
          setPage((page) => {
            if (page === 0) return pageCount - 1;
            return page - 1;
          });
        }}
        className="m-5"
      />

      <div className={styles["journal-container"]}>
        {journals
          .slice(
            displayedJournalsCount * page,
            displayedJournalsCount * (page + 1),
          )
          .map((journalId, index) => {
            return (
              <Journal key={index} id={journalId.id} title={journalId.title} />
            );
          })}
      </div>

      <MdArrowForwardIos
        size={32}
        color="black"
        title="Previous Page"
        onClick={() => {
          setPage((page) => {
            if (page === pageCount - 1) return 0;
            return page + 1;
          });
        }}
        className="m-5"
      />
    </div>
  );
}
