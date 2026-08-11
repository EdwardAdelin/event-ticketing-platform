import { useState } from 'react';
import { Link } from 'react-router-dom';

export default function Navbar() {
    const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

    return (
        <nav className="bg-gray-800">
            <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                <div className="flex h-16 items-center justify-between">

                    {/* Logo/Name of business */}
                    <Link to="/" className="text-xl font-bold text-white">
                        EventHub
                    </Link>

                    {/* Desktop navigation */}
                    <div className="hidden sm:flex sm:space-x-4">
                        <Link
                            to="/"
                            className="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white"
                        >
                            Home
                        </Link>

                        <Link
                            to="/events"
                            className="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white"
                        >
                            Events
                        </Link>

                        <Link
                            to="/about"
                            className="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white"
                        >
                            About
                        </Link>
                    </div>

                    {/* Mobile button */}
                    <button
                        onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
                        className="rounded-md p-2 text-gray-300 sm:hidden"
                    >
                        ☰
                    </button>
                </div>

                {/* Mobile navigation */}
                {/* 
                true && "stuff" -> "stuff" (things render on the screen (for example below, the meniu for mobile when opened)) ; 
                false * "stuff" ->  false (nothing renders on the screen from what`s below)
                ---
                this is kinda basic but maybe it won`t make sense if i forget how this stuff works in Js/Ts in the future :)
                */}

                {mobileMenuOpen && (
                    <div className="space-y-1 pb-3 sm:hidden">
                        <Link
                            to="/"
                            onClick={() => setMobileMenuOpen(false)}
                            className="block rounded-md px-3 py-2 text-base font-medium text-gray-300 hover:bg-gray-700 hover:text-white"
                        >
                            Home
                        </Link>

                        <Link
                            to="/events"
                            onClick={() => setMobileMenuOpen(false)}
                            className="block rounded-md px-3 py-2 text-base font-medium text-gray-300 hover:bg-gray-700 hover:text-white"
                        >
                            Events
                        </Link>

                        <Link
                            to="/about"
                            onClick={() => setMobileMenuOpen(false)}
                            className="block rounded-md px-3 py-2 text-base font-medium text-gray-300 hover:bg-gray-700 hover:text-white"
                        >
                            About
                        </Link>
                    </div>
                )}
            </div>
        </nav>
    );
}