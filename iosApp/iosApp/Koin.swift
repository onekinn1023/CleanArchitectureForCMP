//
//  Koin.swift
//  iosApp
//
//  Created by qing.wang on 9/4/24.
//  Copyright © 2024 orgName. All rights reserved.
//
import Foundation
import ComposeApp

typealias KoinApplication = Koin_coreKoinApplication
typealias Koin = Koin_coreKoin

extension KoinApplication {
    #if DEBUG
        static let shared = companion.start(logEnabled: true)
    #else
        static let shared = companion.start(logEnabled: false)
    #endif
    
    @discardableResult
    static func start() -> KoinApplication {
        shared
    }
}
